package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeveloperApplyPage {
    private WebDriver driver;

    @FindBy(tagName ="h1")
    WebElement heading;

    @FindBy(id="talent_create_applicant_talent_kind")
    WebElement developer_kind;

    @FindBy(id="talent_create_applicant_email")
    WebElement developer_email;

    @FindBy(id="talent_create_applicant_password")
    WebElement developer_password;

    @FindBy(id="talent_create_applicant_password_confirmation")
    WebElement developer_password_confirmation;

    @FindBy(id="talent_create_applicant_full_name")
    WebElement developer_full_name;

    @FindBy(id="save_new_talent_create_applicant")
    WebElement join_toptal_button;

    //Constructor
    public DeveloperApplyPage(WebDriver driver){
        this.driver=driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void setDeveloper_kind(String kind){
        developer_kind.clear();
        developer_kind.sendKeys(kind);
    }

    public void setDeveloper_email(String email){
        developer_email.clear();
        developer_email.sendKeys(email);
    }

    public void setDeveloper_password(String password){
        developer_password.clear();
        developer_password.sendKeys(password);
    }

    public void  setDeveloper_password_confirmation(String password_confirmation){
        developer_password_confirmation.clear();
        developer_password_confirmation.sendKeys(password_confirmation);
    }

    public void setDeveloper_full_name (String fullname){
        developer_full_name.clear();
        developer_full_name.sendKeys(fullname);
    }

    public void clickOnJoin(){
        join_toptal_button.click();
    }
    public boolean isPageOpened(){
        //Assertion
        return heading.getText().toString().contains("Apply to Join");
    }
}