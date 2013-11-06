package it.f2informatica.webapp.menu;

import com.google.common.base.Predicate;
import it.f2informatica.webapp.security.Authority;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Utility package class to instantiate predicates
 * for MenuComposite class filters
 */
class MenuPredicates {

	/**
	 * Predicate that filters by {@code label}
	 */
	static class PredicateSearchByLabel implements Predicate<Menu> {
		private String label;

		public PredicateSearchByLabel(String label) {
			this.label = label;
		}

		@Override
		public boolean apply(Menu input) {
			return input.getLabel().equals(label);
		}
	}

	/**
	 * Predicate that filters by {@code url}
	 */
	static class PredicateSearchByUrl implements Predicate<Menu> {
		private String url;

		public PredicateSearchByUrl(String url) {
			this.url = url;
		}

		@Override
		public boolean apply(Menu input) {
			return input.getUrl().equals(url);
		}
	}

	/**
	 * Predicate that filters by {@link java.util.Set<Authority>}
	 */
	static class PredicateSearchByAuthority implements Predicate<Set<Authority>> {
		private Set<Authority> authorities;

		public PredicateSearchByAuthority(Set<Authority> authorities) {
			this.authorities = authorities;
		}

		@Override
		public boolean apply(Set<Authority> input) {
			return CollectionUtils.containsAny(authorities, input);
		}
	}

	/**
	 * Predicate that filters exactly by {@link java.util.Set<Authority>}
	 */
	static class PredicateSearchExactlyByAuthority implements Predicate<Set<Authority>> {
		private Set<Authority> authorities;

		public PredicateSearchExactlyByAuthority(Set<Authority> authorities) {
			this.authorities = authorities;
		}

		@Override
		public boolean apply(Set<Authority> input) {
			return input.equals(authorities);
		}
	}

}
