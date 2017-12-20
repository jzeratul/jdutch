package com.vladv.jdutch;

import org.apache.wicket.Page;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import com.vladv.jdutch.domain.StorageService;

@SpringBootApplication
public class JDutchApplication extends WicketBootStandardWebApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private StorageService service;

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	public StorageService getStorageService() {
		return service;
	}

	@Override
	protected void init() {
		super.init();

		getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
	}

	public static JDutchApplication getApp() {
		return (JDutchApplication) JDutchApplication.get();
	}

	public static void main(String[] args) {
		SpringApplication.run(JDutchApplication.class, args);
	}
}
