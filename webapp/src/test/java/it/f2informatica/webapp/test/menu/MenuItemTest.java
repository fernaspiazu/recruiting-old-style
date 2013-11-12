package it.f2informatica.webapp.test.menu;

import com.google.common.collect.Sets;
import it.f2informatica.webapp.menu.MenuItem;
import it.f2informatica.webapp.menu.MenuItemException;
import it.f2informatica.mongodb.domain.constants.Authority;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MenuItemTest {

	private MenuItem item1;
	private MenuItem item2;

	@Before
	public void setUp() {
		item1 = new MenuItem("label 1", "/label1");
		item2 = new MenuItem("label 2", "/label2");
	}

	@Test
	public void getLabel() {
		assertThat("label 1").isEqualTo(item1.getLabel());
	}

	@Test
	public void getUrl() {
		assertThat("/label1").isEqualTo(item1.getUrl());
	}

	@Test
	public void testComparisionWithNull() {
		assertThat(item1).isNotEqualTo(null);
	}

	@Test
	public void testComparisionWithAnotherObjectType() {
		assertThat(item1).isNotEqualTo("other object");
	}

	@Test
	public void testComparisionWithSameReference() {
		assertThat(item1).isEqualTo(item1);
	}

	@Test
	public void menuItemEquals() {
		MenuItem itemEqual = new MenuItem("label 1", "/label1");
		assertThat(item1).isEqualTo(itemEqual);
	}

	@Test
	public void menuItemNotEquals() {
		assertThat(item1).isNotEqualTo(item2);
	}

	@Test
	public void menuItemShouldNotBeEqualsWithSameLabel() {
		MenuItem shouldNotBeEqual = new MenuItem("label 1", "/otherUrl");
		assertThat(item1).isNotEqualTo(shouldNotBeEqual);
	}

	@Test
	public void menuItemShouldNotBeEqualsWithSameUrl() {
		MenuItem shouldNotBeEqual = new MenuItem("label -1", "/label1");
		assertThat(item1).isNotEqualTo(shouldNotBeEqual);
	}

	@Test(expected = MenuItemException.class)
	public void verifyExceptionWhenAdding() {
		item1.add(item2);
	}

	@Test(expected = MenuItemException.class)
	public void verifyExceptionWhenAddingWithAuthority() {
		item1.add(item2, null);
	}

	@Test(expected = MenuItemException.class)
	public void verifyExceptionWhenAddingWithAuthorities() {
		item1.addWithAuthorities(item2, Sets.<Authority>newHashSet());
	}

	@Test(expected = NotImplementedException.class)
	public void getItemByLabel() {
		item1.getItemByLabel("foo");
	}

	@Test(expected = NotImplementedException.class)
	public void getItemByUrl() {
		item1.getItemByUrl("/foo");
	}

	@Test(expected = NotImplementedException.class)
	public void getItemsByAuthority() {
		item1.getItemsByAuthority(null);
	}

	@Test(expected = NotImplementedException.class)
	public void getItemsByAuthorities() {
		item1.getItemsByAuthorities(Sets.<Authority>newHashSet());
	}

	@Test(expected = NotImplementedException.class)
	public void getExactlyItemsByAuthorities() {
		item1.getExactlyItemsByAuthorities(Sets.<Authority>newHashSet());
	}

	@Test
	public void toStringTest() {
		assertThat(item1.toString()).isEqualTo(new MenuItem("label 1", "/label1").toString());
	}

}
