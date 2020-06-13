package com.crowd;

import com.crowd.util.CrowdUtil;
import org.junit.After;
import org.junit.Test;

import java.util.Objects;

public class test {



    @Test
    public void test(){
        String s1="123456";
        String s2="123456";
        String s3= CrowdUtil.md5(s1);
        String s4= CrowdUtil.md5(s2);
        if(Objects.equals(s4,s3)) {
            System.out.println("111");
        }
    }
}
