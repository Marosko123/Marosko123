# 1536
import scapy.all as scapy
import constants


def get_destination(arr: str):
    string = arr[0:12]
    return ":".join(string[j:j+2] for j in range(0, len(string), 2))


def get_source(arr: str):
    string = arr[12:24]
    return ":".join(string[j:j + 2] for j in range(0, len(string), 2))


def get_frame_type(arr: str):
    ether_or_length_bytes = str(arr[24:28])
    dsap_bytes = str(arr[28:30])
    ssap_bytes = str(arr[30:32])
    ether_bytes_of_snap = str(arr[40:44])

    if constants.ETHER_TYPE_VALUES.keys().__contains__(ether_or_length_bytes):
        return 'Ethernet II - ' + constants.ETHER_TYPE_VALUES[ether_or_length_bytes]
    elif dsap_bytes != ssap_bytes and not constants.IEEE_SAPS.keys().__contains__(dsap_bytes):
        return 'Novell 802.3 RAW (Novell proprietary)'
    elif not constants.ETHER_TYPE_VALUES.keys().__contains__(ether_bytes_of_snap):
        return 'IEEE 802.3 LLC'
    else:
        return 'IEEE 802.3 LLC + SNAP - ' + constants.ETHER_TYPE_VALUES[ether_bytes_of_snap]


def format_frame(arr: str):
    row_length = 32  # in bytes
    result = ''
    a = 0
    for i in range(0, len(arr), row_length):
        s = arr[i: i+row_length]
        result += f'{"%04d" % a}:  {" ".join(s[j:j+2] for j in range(0, len(s), 2))}\n'
        a += 1
    return result.strip()


def stringify_output(tmp_frame: bytes, order: int):
    frame_as_hex = tmp_frame.hex()
    packet_length = len(tmp_frame)
    return f'\n----------------------------- {order + 1} ------------------------------\n'\
           f'\nReal packet length:           {packet_length + 4 if packet_length > 64 else 64} B' \
           f'\nPCAP API packet length:       {packet_length} B' \
           f'\nSource MAC address:           {get_source(frame_as_hex)} ' \
           f'\nDestination MAC address:      {get_destination(frame_as_hex)}' \
           f'\nPacket type:                  {get_frame_type(frame_as_hex)} ' \
           f'\nFrame:                        \n{format_frame(frame_as_hex)}\n'


file_of_results = open('results.txt', 'w')
frame_analysis = []
file_name = 'eth-1.pcap'
pcap = scapy.rdpcap("./vzorky_pcap_na_analyzu/" + file_name)
i = 0

for iteration, pckt in enumerate(pcap):
    frame_analysis.append([])
    frame = scapy.raw(pckt)
    packet = bytearray(frame)
    frame_analysis[i] = stringify_output(frame, i)
    i += 1

file_of_results.writelines(frame_analysis)
