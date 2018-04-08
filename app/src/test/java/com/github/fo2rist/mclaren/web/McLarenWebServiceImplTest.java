package com.github.fo2rist.mclaren.web;

import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
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
public class McLarenWebServiceImplTest {

    private OkHttpClient mockHttpClient;
    private McLarenWebServiceImpl webservice;

    @Before
    public void setUp() throws Exception {
        mockHttpClient = mock(OkHttpClient.class);
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mock(Call.class));

        webservice = new McLarenWebServiceImpl(mockHttpClient);
    }

    @Test
    public void test_requestLatestFeed_callsWebClient() {
        webservice.requestLatestFeed(mock(FeedRequestCallback.class));

        verify(mockHttpClient).newCall(any(Request.class));
    }

    @Test
    public void test_requestFeedPage_callsWebClient() throws Exception {
        webservice.requestFeedPage(1, mock(FeedRequestCallback.class));

        verify(mockHttpClient).newCall(any(Request.class));
    }

    @Test
    public void test_requestTransmission_callsWebClient() {
        webservice.requestTransmission(mock(TransmissionWebService.TransmissionRequestCallback.class));

        verify(mockHttpClient).newCall(any(Request.class));
    }
}
