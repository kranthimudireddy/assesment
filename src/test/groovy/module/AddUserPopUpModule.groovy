package module

import geb.Module
import geb.module.Checkbox
import geb.module.RadioButtons
import geb.module.Select
import model.UserTableModel



class AddUserPopUpModule extends Module {

    static content = {
        firstNameInput { $('[name="FirstName"]') }
        lastNameInput { $('[name="LastName"]') }
        userNameInput { $('[name="UserName"]') }
        passwordInput { $('[name="Password"]') }
        customerRadioButton { $('[name="optionsRadios"]').module(RadioButtons) }
        roleDropDown { $('[name="RoleId"]').module(Select) }
        emailInput { $('[name="Email"]') }
        cellPhoneInput { $('[name="Mobilephone"]') }
        saveButton { $('.btn-success') }
    }


    public void fillInputs(UserTableModel userTableModel) {
        firstNameInput = userTableModel.getFirstName()
        lastNameInput = userTableModel.getLastName()
        userNameInput = userTableModel.getUserName()
        passwordInput = userTableModel.getPassword()
        customerRadioButton.setChecked(userTableModel.getCusomter())
        roleDropDown.setSelected(userTableModel.getRole())
        emailInput = userTableModel.getEmail()
        cellPhoneInput = userTableModel.getCell()
    }

    public clickSave() {
        saveButton.click()
    }

}
