package com.vladv.jdutch;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import com.vladv.jdutch.articletest.ArticleTest;
import com.vladv.jdutch.articletest.ArticleTestRepository;
import com.vladv.jdutch.gatentekst.GatenTekstTest;
import com.vladv.jdutch.gatentekst.GatenTekstTestRepository;
import com.vladv.jdutch.home.HomePage;
import com.vladv.jdutch.verbtest.VerbTest;
import com.vladv.jdutch.verbtest.VerbTestRepository;
import com.vladv.jdutch.wordtest.WordTest;
import com.vladv.jdutch.wordtest.WordTestRepository;

@SpringBootApplication
public class JDutchApplication extends WicketBootStandardWebApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private GatenTekstTestRepository gaatenTestRepository;
	
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

	public GatenTekstTestRepository getGaatenTestRepository() {
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

	public static List<ArticleTest> getAllArticles() {
		return JDutchApplication.getApp().getArticleTestRepository().findAll();
	}

	public static List<GatenTekstTest> getAllGatenTeksts() {
		return JDutchApplication.getApp().getGaatenTestRepository().findAll();
	}

	public static List<WordTest> getAllWordTests() {
		return JDutchApplication.getApp().getWordTestRepository().findAll();
	}

	public static List<VerbTest> getAllVerbs() {
		return JDutchApplication.getApp().getVerbTestRepository().findAll();
	}
}
