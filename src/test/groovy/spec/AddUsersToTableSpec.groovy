package spec

import data.UserTableData
import geb.spock.GebReportingSpec
import page.WebTablesPage
import spock.lang.Shared

class AddUsersToTableSpec extends GebReportingSpec {

    @Shared WebTablesPage webTablesPage
    @Shared user1 = UserTableData.user1
    @Shared user2 = UserTableData.user2

    def "test"() {
        given: 'Navigate to User List Table & Validate that you are on the User List Table'
            to WebTablesPage
            webTablesPage = at WebTablesPage
        when: 'Click on add user, and add users with following details'
            webTablesPage.createUser(user1)
        then: "verify user1 are added to the list"
            webTablesPage.verifyAllFields(user1)

        when: 'Click on add user, and add users with following details'
            webTablesPage.createUser(user2)
        then: "verify user2 are added to the list"
            webTablesPage.verifyAllFields(user2)
    }
}