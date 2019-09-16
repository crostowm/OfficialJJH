import readers.WSRMap;

public class Main
{
  public static void main(String[] args)
  {
    WSRMap wsrMap = new WSRMap("WeeklySalesRS08-crostowm.csv");
    System.out.println(wsrMap.getDataForShift(WSRMap.ADJUSTED_SALES, 5));
  }
}
