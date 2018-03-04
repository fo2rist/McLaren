package com.github.fo2rist.mclaren.web;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class McLarenFeedWebServiceImplTest {

    OkHttpClient mockHttpClient;
    McLarenFeedWebServiceImpl webservice;

    @Before
    public void setUp() throws Exception {
        mockHttpClient = mock(OkHttpClient.class);
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mock(Call.class));

        webservice = new McLarenFeedWebServiceImpl(mockHttpClient);
    }

    @Test
    public void test_requestLatestFeed_callsWebClient() {
        webservice.requestLatestFeed(mock(FeedWebServiceCallback.class));

        verify(mockHttpClient).newCall(any(Request.class));
    }

    @Test
    public void test_requestFeedPage_callsWebClient() throws Exception {
        webservice.requestFeedPage(1, mock(FeedWebServiceCallback.class));

        verify(mockHttpClient).newCall(any(Request.class));
    }
}
