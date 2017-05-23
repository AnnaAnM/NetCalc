package ru.asoloveva.netcalc.feature.calculation;

public class IpCalc {
    private final int DEFAULT_MASK = 0b11111111;

    private final int[] ipAdds = new int[4];
    private final int[] masks = new int[4];
    private final int[] subnet = new int[4];
    private final int[] wcMasks = new int[4];
    private final int[] bCastAdds = new int[4];

    private String ipAddress;
    private String mask;
    private String subnetAdd;
    private String wildcardMask;
    private String bCastAdd;

    public IpCalc(String ipAddress, String mask) {
        this.ipAddress = ipAddress;
        this.mask = mask;
        splitIp(ipAdds, this.ipAddress);
        splitIp(masks, this.mask);
        findSubnet();
        findWildcardMask();
        findBroadcastAdd();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getSubnetAddress() {
        return subnetAdd;
    }

    public String getWildcardMask() {
        return wildcardMask;
    }

    public String getBroadcastAddress() {
        return bCastAdd;
    }

    public void setIp(String ipAddress) {
        this.ipAddress = ipAddress;
        splitIp(ipAdds, ipAddress);
        findSubnet();
        findWildcardMask();
        findBroadcastAdd();
    }

    public void setMask(String mask) {
        this.mask = mask;
        splitIp(masks, mask);
        findSubnet();
        findWildcardMask();
        findBroadcastAdd();
    }

    public String getNumberOfAdds(int slashMask) {
        int i = (int) (Math.pow(2, 32 - slashMask) - 2);
        if (i < 0) {
            i = 0;
        }
        return String.valueOf(i);
    }

    private void findSubnet() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < subnet.length; i++) {
            subnet[i] = ipAdds[i] & masks[i] & DEFAULT_MASK;
            sb.append(subnet[i]);
            if (i < subnet.length - 1) {
                sb.append(".");
            }
        }

        subnetAdd = sb.toString();
    }

    private void findWildcardMask() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wcMasks.length; i++) {
            wcMasks[i] = (~masks[i]) & DEFAULT_MASK;
            sb.append(wcMasks[i]);
            if (i < wcMasks.length - 1) {
                sb.append(".");
            }
        }

        wildcardMask = sb.toString();
    }

    private void findBroadcastAdd() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bCastAdds.length; i++) {
            bCastAdds[i] = subnet[i] | wcMasks[i];
            sb.append(bCastAdds[i]);
            if (i < bCastAdds.length - 1) {
                sb.append(".");
            }
        }

        bCastAdd = sb.toString();
    }

    private void splitIp(int[] ipObjects, String ipToSplit) {
        String[] ipStrings = ipToSplit.split("\\.");
        for (int i = 0; i < ipStrings.length; i++) {
            ipObjects[i] = Integer.parseInt(ipStrings[i]);
        }
    }
}
