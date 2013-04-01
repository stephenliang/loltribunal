package com.simplyintricate.Tribunal.model;

/**
 * Created with IntelliJ IDEA.
 * User: Stephen
 * Date: 3/21/13
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Captcha {
    private String recaptchaImageUrl;
    private String recaptchaChallenge;

    public Captcha(String challenge, String imageUrl)
    {
        this.recaptchaChallenge = challenge;
        this.recaptchaImageUrl = imageUrl;
    }

    public String getRecaptchaChallenge() {
        return recaptchaChallenge;
    }

    public void setRecaptchaChallenge(String recaptchaChallenge) {
        this.recaptchaChallenge = recaptchaChallenge;
    }

    public String getRecaptchaImageUrl() {
        return recaptchaImageUrl;
    }

    public void setRecaptchaImageUrl(String recaptchaImageUrl) {
        this.recaptchaImageUrl = recaptchaImageUrl;
    }
}
