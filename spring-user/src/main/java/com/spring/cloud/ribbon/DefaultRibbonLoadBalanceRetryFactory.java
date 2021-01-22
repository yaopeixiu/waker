package com.spring.cloud.ribbon;


import com.spring.cloud.ribbon.listener.RibbonRetryListener;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.retry.RetryListener;

public class DefaultRibbonLoadBalanceRetryFactory extends RibbonLoadBalancedRetryFactory {
    public DefaultRibbonLoadBalanceRetryFactory(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public RetryListener[] createRetryListeners(String service) {
        return new RetryListener[]{new RibbonRetryListener()};
    }
}
