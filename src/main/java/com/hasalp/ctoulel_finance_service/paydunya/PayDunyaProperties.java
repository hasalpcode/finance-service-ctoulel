package com.hasalp.ctoulel_finance_service.paydunya;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "paydunya")
public class PayDunyaProperties {

    @PostConstruct
    void validate() {
        if (!StringUtils.hasText(masterKey) || !StringUtils.hasText(privateKey) || !StringUtils.hasText(token)) {
            throw new IllegalStateException(
                    "Identifiants PayDunya manquants : verifie que PAYDUNYA_MASTER_KEY, "
                            + "PAYDUNYA_PRIVATE_KEY et PAYDUNYA_TOKEN sont bien visibles par le processus "
                            + "qui lance ctoulel-finance-service (pas seulement dans le shell).");
        }
        if (sandbox && (!StringUtils.hasText(sandboxTestPhone) || !StringUtils.hasText(sandboxTestEmail)
                || !StringUtils.hasText(sandboxTestPassword))) {
            throw new IllegalStateException(
                    "Compte de test PayDunya manquant : en mode sandbox, verifie que "
                            + "PAYDUNYA_SANDBOX_PHONE, PAYDUNYA_SANDBOX_EMAIL et PAYDUNYA_SANDBOX_PASSWORD "
                            + "sont renseignes (compte de test fourni par PayDunya, pas le client final).");
        }
    }

    private String baseUrl;
    private String disburseBaseUrl;
    private String masterKey;
    private String privateKey;
    private String publicKey;
    private String token;
    private String storeName;
    private String callbackUrl;
    private String disburseCallbackUrl;
    private String returnUrl;
    private String cancelUrl;
    private boolean sandbox;
    private String sandboxTestPhone;
    private String sandboxTestEmail;
    private String sandboxTestPassword;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getDisburseBaseUrl() {
        return disburseBaseUrl;
    }

    public void setDisburseBaseUrl(String disburseBaseUrl) {
        this.disburseBaseUrl = disburseBaseUrl;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getDisburseCallbackUrl() {
        return disburseCallbackUrl;
    }

    public void setDisburseCallbackUrl(String disburseCallbackUrl) {
        this.disburseCallbackUrl = disburseCallbackUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getSandboxTestPhone() {
        return sandboxTestPhone;
    }

    public void setSandboxTestPhone(String sandboxTestPhone) {
        this.sandboxTestPhone = sandboxTestPhone;
    }

    public String getSandboxTestEmail() {
        return sandboxTestEmail;
    }

    public void setSandboxTestEmail(String sandboxTestEmail) {
        this.sandboxTestEmail = sandboxTestEmail;
    }

    public String getSandboxTestPassword() {
        return sandboxTestPassword;
    }

    public void setSandboxTestPassword(String sandboxTestPassword) {
        this.sandboxTestPassword = sandboxTestPassword;
    }
}
