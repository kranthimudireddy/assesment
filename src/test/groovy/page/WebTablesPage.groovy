package page

import geb.Page
import geb.navigator.Navigator
import groovy.util.logging.Log4j
import model.UserTableModel
import module.AddUserPopUpModule
import org.hamcrest.MatcherAssert

@Log4j
class WebTablesPage extends Page {

    static at = { $('[ng-controller="mainCtrl"]').displayed }

    static content = {
        addUser { $("[ng-show='actions.add.url'] .btn-link") }
        userRowsList { $(".smart-table-data-row") }
        userNameText { name -> userRowsList.any { it.find('td:nth-of-type(3)').text() == name } }
        addUserModule(required: true, wait: true) { module(AddUserPopUpModule)}
    }

    public createUser(UserTableModel userTableModel) {
       if (!userNameText(userTableModel.getUserName())) {
            addUser.click()
            waitFor {
                addUserModule.isDisplayed()
            }
            addUserModule.fillInputs(userTableModel)
        addUserModule.clickSave()

        } else {
            log.info("UserName already exists cannot create")
        }
    }

    public boolean verifyAllFields(UserTableModel userTableModel){
        List<Navigator> rows = userRowsList.asList()
        for(Navigator row : rows){
            if (row.find("td:nth-child(3)").text() == userTableModel.getUserName()) {
                List<Navigator> columns = row.find("td").asList()
                    MatcherAssert.assertThat("Firstname not matching" , columns.get(0).text().equals(userTableModel.getFirstName()))
                    MatcherAssert.assertThat("Lastname not matching" , columns.get(1).text().equals(userTableModel.getLastName()))
                    MatcherAssert.assertThat("Username not matching" , columns.get(2).text().equals(userTableModel.getUserName()))
                    if (!columns.get(3).text().isEmpty())
                        MatcherAssert.assertThat("Customer not matching" , columns.get(3).text().equals(userTableModel.getCusomter()))
                    MatcherAssert.assertThat("Role not matching" , columns.get(5).text().equals(userTableModel.getRole()))
                    MatcherAssert.assertThat("Email not matching" , columns.get(6).text().equals(userTableModel.getEmail()))
                    MatcherAssert.assertThat("Cell not matching" , columns.get(7).text().equals(userTableModel.getCell()))
            }
        }
        true
    }

}
