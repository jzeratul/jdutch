package com.vladv.jdutch.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vladv.jdutch.JDutchApplication;
import com.vladv.jdutch.articletest.ArticleTest;
import com.vladv.jdutch.articletest.ArticleTestRepository;
import com.vladv.jdutch.domain.Test;
import com.vladv.jdutch.gatentekst.GatenTekstTest;
import com.vladv.jdutch.gatentekst.GatenTekstTestRepository;
import com.vladv.jdutch.wordtest.WordTest;
import com.vladv.jdutch.wordtest.WordTestRepository;

public enum IMPORT_EXPORT {

	GATEN_IM(1) {
		@Override
		public MongoRepository<? extends Test, Long> getRepo() {
			return JDutchApplication.getApp().getGatenTekstTestRepository();
		}

		@Override
		public void save(Test test) {
			GatenTekstTestRepository r = (GatenTekstTestRepository) getRepo();
			GatenTekstTest t = new GatenTekstTest(test.getTestname(), test.getTestcontents());
			r.save(t);
		}
	},
	GATEN_EX(2) {
		@Override
		public MongoRepository<? extends Test, Long> getRepo() {
			return JDutchApplication.getApp().getGatenTekstTestRepository();
		}

		@Override
		public void save(Test test) {
		}
	},

	WORD_IM(3) {
		@Override
		public MongoRepository<? extends Test, Long> getRepo() {
			return JDutchApplication.getApp().getWordTestRepository();
		}

		@Override
		public void save(Test test) {
			WordTestRepository r = (WordTestRepository) getRepo();
			WordTest t = new WordTest(test.getTestname(), test.getTestcontents());
			r.save(t);
		}
	},
	WORD_EX(4) {
		@Override
		public MongoRepository<? extends Test, Long> getRepo() {
			return JDutchApplication.getApp().getWordTestRepository();
		}

		@Override
		public void save(Test test) {
		}
	},

	ARTICLE_IM(5) {
		@Override
		public MongoRepository<? extends Test, Long> getRepo() {
			return JDutchApplication.getApp().getArticleTestRepository();
		}

		@Override
		public void save(Test test) {
			ArticleTestRepository r = (ArticleTestRepository) getRepo();
			ArticleTest t = new ArticleTest(test.getTestname(), test.getTestcontents());
			r.save(t);
		}
	},
	ARTICLE_EX(6) {
		@Override
		public MongoRepository<? extends Test, Long> getRepo() {
			return JDutchApplication.getApp().getArticleTestRepository();
		}

		@Override
		public void save(Test test) {
		}
	},
	DEFAULT(7) {
		@Override
		public MongoRepository<? extends Test, Long> getRepo() {
			return null;
		}

		@Override
		public void save(Test test) {
		}
	}
	;

	private int idx;

	private IMPORT_EXPORT(int idx) {
		this.idx = idx;
	}

	public abstract MongoRepository<? extends Test, Long> getRepo();

	public abstract void save(Test test);

	public static IMPORT_EXPORT get(int idx) {
		IMPORT_EXPORT[] values = IMPORT_EXPORT.values();
		for (IMPORT_EXPORT ie : values) {
			if (ie.idx == idx) {
				return ie;
			}
		}
		return DEFAULT;
	}
}
