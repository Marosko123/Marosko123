# EtherType values
ETHER_TYPE_VALUES: dict = {'0200': 'XEROX PUP',
                           '0201': 'PUP Addr Trans',
                           '0800': 'Internet IP (IPv4)',
                           '0801': 'X.75 Internet',
                           '0805': 'X.25 Level 3',
                           '0806': 'ARP (Address Resolution Protocol',
                           '8035': 'Reverse ARP',
                           '809B': 'Appletalk',
                           '80F3': 'Appletalk AARP (Kinetics)',
                           '8100': 'IEEE 802.1Q VLAN-tagged frames',
                           '8137': 'Novell IPX',
                           '86DD': 'IPv6',
                           '880B': 'PPP',
                           '8847': 'MPLS',
                           '8848': 'MPLS with upstream-assigned label',
                           '8863': 'PPPoE Discovery Stage',
                           '8864': 'PPPoE Session Stage'}

# 802.2 LLC Service Access Points (SAPs)
IEEE_SAPS: dict = {'00': 'Null SAP',
                   '02': 'LLC Sublayer Management / Individual',
                   '03': 'LLC Sublayer Management / Group',
                   '06': 'IP (DoD Internet Protocol)',
                   '0E': 'PROWAY (IEC 955) Network Management, Maintenance and Installation',
                   '42': 'BPDU (Bridge PDU / 802.1 Spanning Tree)',
                   '4E': 'MMS (Manufacturing Message Service) EIA-RS 511',
                   '5E': 'ISI IP',
                   '7E': 'X.25 PLP (ISO 8208)',
                   '8E': 'PROWAY (IEC 955) Active Station List Maintenance',
                   'AA': 'SNAP (Sub-Network Access Protocol / non-IEEE SAPs)',
                   'E0': 'IPX (Novell NetWare)',
                   'F4': 'LAN Management',
                   'FE': 'ISO Network Layer Protocols',
                   'FF': 'Global DSAP'}



