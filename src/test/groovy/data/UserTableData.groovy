package data

import model.UserTableModel

class UserTableData {

    public static final UserTableModel user1 = new UserTableModel(firstName: "FName1", lastName: "LName1", userName: "User1", password: "Pass1",
            cusomter: "Company AAA", role: "Admin", email: "admin@mail.com", cell: "082555")

    public static final UserTableModel user2 = new UserTableModel(firstName: "FName2", lastName: "LName2", userName: "User2", password: "Pass2",
            cusomter: "Company BBB", role: "Customer", email: "customer@mail.com", cell: "083444")
}
