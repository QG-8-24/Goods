package com.com.example.goods;

import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.entity.Inv;
import com.com.example.goods.entity.Type;
import com.com.example.goods.entity.User;
import com.com.example.goods.entity.vo.Instorage;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href='http://d.android.com/tools/testing'>Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() throws IOException {
        String s = "[{\"id\":1,\"code\":\"R1592317721374\",\"company\":\"tyc责任有限公司\",\"department\":\"谭家沟\",\"phone\":\"120\",\"intime\":\"2020-06-16T14:28:41.000+00:00\",\"type\":2,\"linkman\":\"谭云川\",\"goodsidsList\":[1],\"amountList\":[10],\"goodsList\":[{\"image\":\"\",\"unit\":\"个\",\"amount\":10,\"code\":\"123456\",\"goodsId\":1,\"name\":\"cs专用防毒面罩\",\"typeName\":\"防毒面具\",\"producer\":\"马强医药责任有限公司\",\"remark\":\"芜湖~\",\"typeId\":5,\"specifications\":\"2个/盒\"}]}]";

        List list = JsonUtils.string2Object(s, List.class);
        for (Object o : list) {
            String string = JsonUtils.object2String(o);
            Instorage instorage = JsonUtils.string2Object(string, Instorage.class);
            System.out.println(instorage.getCompany());
        }
    }

}