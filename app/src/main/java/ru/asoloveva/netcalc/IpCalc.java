package ru.asoloveva.netcalc;

public class IpCalc {
    private String ipAddress;
    private String mask;
    private String subnetAdd;
    private String wildcardMask;
    private String bCastAdd;
    private int[] ipAdds = new int[4];
    private int[] masks = new int[4];
    private int[] subnet = new int[4];
    private int[] wcMasks = new int[4];
    private int[] bCastAdds = new int[4];

    private final int maskForMask = 0b11111111;

    public IpCalc(String ipAddress, String mask){
        this.ipAddress = ipAddress;
        this.mask = mask;
        SplitIp(ipAdds, this.ipAddress);
        SplitIp(masks, this.mask);
        FindSubnet();
        FindWildcardMask();
        FindBroadcastAdd();
    }

    public String IpAddress(){
        return ipAddress;
    }

    public String SubnetAddress(){
        return  subnetAdd;
    }

    public String WildcardMask(){
        return wildcardMask;
    }

    public  String BroadcastAddress(){
        return bCastAdd;
    }

    public void SetIp(String ipAddress){
        this.ipAddress = ipAddress;
        SplitIp(ipAdds, ipAddress);
        FindSubnet();
        FindWildcardMask();
        FindBroadcastAdd();
    }

    public  void  SetMask(String mask){
        this.mask = mask;
        SplitIp(masks, mask);
        FindSubnet();
        FindWildcardMask();
        FindBroadcastAdd();
    }

    public String NumberOfAdds(int slashMask){
        int i = (int)(Math.pow(2, 32 - slashMask) - 2);
        if (i < 0) i = 0;
        return "" + i;
    }

    private void SplitIp(int[] ipObjects, String ipToSplit){
        String[] ipStrings = ipToSplit.split("\\.");
        //int[] intIpSplit = new int[4];
        for (int i = 0; i < ipStrings.length; i++){
            ipObjects[i] = Integer.parseInt(ipStrings[i]);
        }
    }

    private void FindSubnet(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < subnet.length; i++){
            subnet[i] = ipAdds[i] & masks[i] & maskForMask;
            sb.append(subnet[i]);
            if (i < subnet.length - 1){
                //subnetAdd += ".";
                sb.append(".");
            }
        }

        subnetAdd = sb.toString();
    }

    private void FindWildcardMask() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wcMasks.length; i++){
            wcMasks[i] = (~masks[i]) & maskForMask;
            sb.append(wcMasks[i]);
            if (i < wcMasks.length - 1){
                sb.append(".");
            }
        }

        wildcardMask = sb.toString();
    }

    private void FindBroadcastAdd(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bCastAdds.length; i++){
            bCastAdds[i] = subnet[i] | wcMasks[i];
            sb.append(bCastAdds[i]);
            if (i < bCastAdds.length - 1){
                sb.append(".");
            }
        }

        bCastAdd = sb.toString();
    }
}
