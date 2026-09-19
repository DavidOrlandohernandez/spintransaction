package com.spin.transaction.client;

import com.spin.transaction.dto.provider.ProviderRequest;
import com.spin.transaction.dto.provider.ProviderResponse;

public interface TransactionExecutor {

    ProviderResponse execute(ProviderRequest request);

}
