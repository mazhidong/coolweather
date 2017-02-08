package cherish.com.coolweather.util;

/**
 * Created by cherish on 2017/2/9.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
