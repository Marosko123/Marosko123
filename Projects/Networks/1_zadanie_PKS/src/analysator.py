# 1536
import scapy.all as scapy
import constants


def get_destination(arr: str):
    string = arr[0:12]
    return ":".join(string[j:j + 2] for j in range(0, len(string), 2))


def get_source(arr: str):
    string = arr[12:24]
    return ":".join(string[j:j + 2] for j in range(0, len(string), 2))


def get_frame_type(arr: str):
    ether_or_length_bytes = str(arr[24:28])
    dsap_bytes = str(arr[28:30])
    ssap_bytes = str(arr[30:32])
    ether_bytes_of_snap = str(arr[40:44])

    if constants.ETHER_TYPE_VALUES.keys().__contains__(ether_or_length_bytes):
        if ether_or_length_bytes == '0800':
            source_ip_address = get_ip_source(arr)
            if not IP_addresses.__contains__(source_ip_address):
                IP_addresses.append(source_ip_address)
            if most_sent_packets.keys().__contains__(source_ip_address):
                most_sent_packets[source_ip_address] += 1
            else:
                most_sent_packets[source_ip_address] = 1
        return 'Ethernet II - ' + constants.ETHER_TYPE_VALUES[ether_or_length_bytes]
    elif dsap_bytes != ssap_bytes and not constants.IEEE_SAPS.keys().__contains__(dsap_bytes):
        return 'Novell 802.3 RAW (Novell proprietary)'
    elif not constants.ETHER_TYPE_VALUES.keys().__contains__(ether_bytes_of_snap):
        return 'IEEE 802.3 LLC'
    else:
        return 'IEEE 802.3 LLC + SNAP - ' + constants.ETHER_TYPE_VALUES[ether_bytes_of_snap]


def get_ip_source(arr: str):
    return '.'.join(list(str(int(arr[52:60][i:i+2], 16)) for i in range(0, len(arr[52:60]), 2)))


def get_ip_destination(arr: str):
    return '.'.join(list(str(int(arr[60:68][i:i+2], 16)) for i in range(0, len(arr[60:68]), 2)))


def get_ip_sent_most_packets():
    most = 0
    ip = ''
    for key in most_sent_packets.keys():
        if most_sent_packets[key] > most:
            most = most_sent_packets[key]
            ip = key
    return ip


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
IP_addresses = []
most_sent_packets: dict = {}

for iteration, pckt in enumerate(pcap):
    frame_analysis.append([])
    frame = scapy.raw(pckt)
    packet = bytearray(frame)
    frame_analysis[i] = stringify_output(frame, i)
    i += 1

most_sending_ip = get_ip_sent_most_packets()
frame_analysis.append('\nIP addresses of the protocols TCP/IPv4: \n'+'\n'.join(IP_addresses))
frame_analysis.append(f'\nMost sent packets from IP: {most_sending_ip} ({most_sent_packets[most_sending_ip]} pcs)\n')

file_of_results.writelines(frame_analysis)
