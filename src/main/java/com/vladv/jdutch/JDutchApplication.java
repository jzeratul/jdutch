package com.vladv.jdutch;

import java.util.List;
import java.util.stream.Collectors;

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
import com.vladv.jdutch.verbtest.VerbTest;
import com.vladv.jdutch.verbtest.VerbTestRepository;
import com.vladv.jdutch.wordtest.WordTest;
import com.vladv.jdutch.wordtest.WordTestRepository;

@SpringBootApplication
public class JDutchApplication extends WicketBootStandardWebApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private GatenTekstTestRepository gatenTekstTestRepository;
	
	@Autowired
	private VerbTestRepository verbTestRepository;

	@Autowired
	private ArticleTestRepository articleTestRepository;
	
	@Autowired
	private WordTestRepository wordTestRepository;

	public GatenTekstTestRepository getGatenTekstTestRepository() {
		return gatenTekstTestRepository;
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

	public static List<ArticleTest> getAllArticles(String ofCategory) {
		return JDutchApplication.getApp().getArticleTestRepository().findAll();
	}

	public static List<GatenTekstTest> getAllGatenTeksts(String ofCategory) {
		return JDutchApplication.getApp().getGatenTekstTestRepository().findAll();
	}

	public static List<WordTest> getAllWordTests(String ofCategory) {
    return JDutchApplication.getApp().getWordTestRepository().findAll();
	}

	public static List<VerbTest> getAllVerbs(String ofCategory) {
		return JDutchApplication.getApp().getVerbTestRepository().findAll();
	}

  public static List<String> getAllGatenTekstCategories() {
    return getAllGatenTeksts(null).stream().map(g -> g.getCategory()).collect(Collectors.toList());
  }

  public static List<String> getAllArticleTestCategories() {
    return getAllArticles(null).stream().map(g -> g.getCategory()).collect(Collectors.toList());
  }

  public static List<String> getAllVerbTestCategories() {
    return getAllVerbs(null).stream().map(g -> g.getCategory()).collect(Collectors.toList());
  }

  public static List<String> getAllWordTestCategories() {
    return getAllWordTests(null).stream().map(g -> g.getCategory()).collect(Collectors.toList());
  }
}
