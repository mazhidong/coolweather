package cherish.com.coolweather;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by cherish on 2017/2/9.
 */

public class testFirst extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void test01() {
        String a = "01|哈哈,02|哇咔咔";
        String[] list = a.split(",");
        String[] c1 = list[1].split("\\|");
        Log.e("1", c1[1]);
    }
}
