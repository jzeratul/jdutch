package com.vladv.jdutch;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vladv.jdutch.domain.ArticleTest;
import com.vladv.jdutch.domain.ArticleTestRepository;
import com.vladv.jdutch.domain.GatenTekstTest;
import com.vladv.jdutch.domain.GatenTekstTestRepository;
import com.vladv.jdutch.domain.VerbTest;
import com.vladv.jdutch.domain.VerbTestRepository;
import com.vladv.jdutch.domain.WordTest;
import com.vladv.jdutch.domain.WordTestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDutchApplication.class)
@Ignore
public class JWicketTest {

	private WicketTester tester;

	@Autowired
	private JDutchApplication wicketApplication;

	@MockBean
	private ArticleTestRepository articleRepo;
	@MockBean
	private GatenTekstTestRepository gatenRepo;
	@MockBean
	private VerbTestRepository verbRepo;
	@MockBean
	private WordTestRepository wordRepo;

	@Before
	public void setUp() {
		tester = new WicketTester(wicketApplication);
		
		List<ArticleTest> articles = Arrays.asList(createArticle("1"), createArticle("2"));
		Mockito.when(articleRepo.findAll()).thenReturn(articles);
		
		List<GatenTekstTest> gatenTeksten = Arrays.asList(createGatenTekst("1"), createGatenTekst("2"));
		Mockito.when(gatenRepo.findAll()).thenReturn(gatenTeksten);
		
		List<VerbTest> verbs = Arrays.asList(createVerbTest("1"), createVerbTest("2"));
		Mockito.when(verbRepo.findAll()).thenReturn(verbs);
		
		List<WordTest> words = Arrays.asList(createWordTest("1"), createWordTest("2"));
		Mockito.when(wordRepo.findAll()).thenReturn(words);
	}

	public ArticleTest createArticle(String id) {
		return new ArticleTest(id, id);
	}

	public GatenTekstTest createGatenTekst(String id) {
		return new GatenTekstTest(id, id);
	}
	
	public VerbTest createVerbTest(String id) {
		return new VerbTest(id, id);
	}
	
	public WordTest createWordTest(String id) {
		return new WordTest(id, id);
	}

	public WicketTester getTester() {
		return tester;
	}

	public JDutchApplication getWicketApplication() {
		return wicketApplication;
	}
}
