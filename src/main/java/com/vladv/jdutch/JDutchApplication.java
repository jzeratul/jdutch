package com.vladv.jdutch;

import org.apache.wicket.Page;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import com.vladv.jdutch.domain.ArticleTestRepository;
import com.vladv.jdutch.domain.GaatenTestRepository;
import com.vladv.jdutch.domain.VerbTestRepository;
import com.vladv.jdutch.domain.WordTestRepository;
import com.vladv.jdutch.pages.HomePage;

@SpringBootApplication
public class JDutchApplication extends WicketBootStandardWebApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private GaatenTestRepository gaatenTestRepository;
	
	@Autowired
	private VerbTestRepository verbTestRepository;

	@Autowired
	private ArticleTestRepository articleTestRepository;
	
	@Autowired
	private WordTestRepository wordTestRepository;

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	public GaatenTestRepository getGaatenTestRepository() {
		return gaatenTestRepository;
	}
	
	public VerbTestRepository getVerbTestRepository() {
		return verbTestRepository;
	}

	public ArticleTestRepository getArticleTestRepository() {
		return articleTestRepository;
	}
	
	public WordTestRepository getWordTestRepository() {
		return wordTestRepository;
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
