package com.vladv.jdutch;

import org.apache.wicket.Page;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import com.vladv.jdutch.domain.TestRepository;
import com.vladv.jdutch.pages.HomePage;

@SpringBootApplication
public class JDutchApplication extends WicketBootStandardWebApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private TestRepository repository;

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	public TestRepository getRepository() {
		return repository;
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
