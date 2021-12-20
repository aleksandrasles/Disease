package com.application.disease.registration;

abstract class TemplateService {

    public void register(RegistrationRequest request)
    {
        signUpUser(request);
        send(request);
    }

   public abstract void signUpUser(RegistrationRequest request);
   public abstract void send(RegistrationRequest request);
}
