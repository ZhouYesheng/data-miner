package le.data.scs.spider;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "{\"employeeUserNick\":[\"cntaobao乐视官方旗舰店\"],\"customerUserNick\":[\"cntaobaogq16880\"],\"employeeAll\":false,\"customerAll\":false,\"start\":\"2017-02-28\",\"end\":\"2017-02-28\",\"beginKey\":null,\"endKey\":null}";
      System.out.println(URLEncoder.encode(url,"utf-8"));
        System.out.println(new Date(1488244784705l));
        System.out.println(URLDecoder.decode("%7B%22employeeNick%22%3A%22cntaobao%E4%B9%90%E8%A7%86%E5%AE%98%E6%96%B9%E6%97%97%E8%88%B0%E5%BA%97%22%2C%22customerNick%22%3A%22cntaobao0325%E5%A4%A9%E5%A4%A9%E5%BF%AB%E4%B9%90%22%2C%22start%22%3A%222017-02-27%22%2C%22end%22%3A%222017-02-27%22%2C%22beginKey%22%3Anull%2C%22endKey%22%3Anull%2C%22employeeAll%22%3Afalse%2C%22customerAll%22%3Afalse%7D","utf-8"));
    }
}
