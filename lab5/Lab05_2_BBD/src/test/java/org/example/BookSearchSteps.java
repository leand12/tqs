package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;


public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();

	@ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDateTime iso8601Date(String year, String month, String day){
		return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0);
	}

	@ParameterType("([0-9]{4})")
	public LocalDateTime iso8601DateYearStart(String year){
		return LocalDateTime.of(Integer.parseInt(year), 1, 1, 0, 0);
	}

	@ParameterType("([0-9]{4})")
	public LocalDateTime iso8601DateYearEnd(String year){
		return LocalDateTime.of(Integer.parseInt(year), 12, 31, 0, 0);
	}

	@Given("a(nother) book with the title {string}, written by {string}, published in {iso8601Date}")
	public void addNewBook(final String title, final String author, final LocalDateTime published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}

	@When("the customer searches for books published between {iso8601DateYearStart} and {iso8601DateYearEnd}")
	public void setSearchParameters(final LocalDateTime from, final LocalDateTime to) {
		result = library.findBooks(from, to);
	}

	@Then("(\\d+) books should have been found$")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertEquals(result.size(), booksFound);
	}

	@Then("Book (\\d+) should have the title '(.+)'$")
	public void verifyBookAtPosition(final int position, final String title) {
		assertEquals(result.get(position - 1).getTitle(), title);
	}
}