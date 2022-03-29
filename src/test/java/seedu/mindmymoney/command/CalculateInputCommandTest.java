package seedu.mindmymoney.command;

import org.junit.jupiter.api.Test;
import seedu.mindmymoney.MindMyMoneyException;
import seedu.mindmymoney.data.CreditCardList;
import seedu.mindmymoney.data.ExpenditureList;
import seedu.mindmymoney.data.IncomeList;
import seedu.mindmymoney.userfinancial.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CalculateInputCommandTest {
    private final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
    private final PrintStream stdout = System.out;

    public void setUp() {
        System.setOut(new PrintStream(capturedOut));
    }

    /**
     * Asserts if user is able to calculate expenditure per month.
     */
    @Test
    void calculateInputCommand_oneInput_expectCorrectOutput() throws MindMyMoneyException {
        ExpenditureList expenditureTestList = new ExpenditureList();
        CreditCardList creditCardTestList = new CreditCardList();
        IncomeList incomeList = new IncomeList();
        User user = new User(expenditureTestList, creditCardTestList, incomeList);

        String inputString = "/e cash /c Personal /d Nike Shoes /a 300 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();
        inputString = "/e cash /c Personal /d Nike Shoes /a 3000 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();
        inputString = "/e cash /c Personal /d Nike Shoes /a 1000 /t 2022-04";
        new AddCommand(inputString, user).executeCommand();
        inputString = "/e cash /c Personal /d Nike Shoes /a 200 /t 2021-03";
        new AddCommand(inputString, user).executeCommand();

        setUp();
        new CalculateInputCommand("/epm Mar 2022", user).executeCommand();
        tearDown();
        String expectedOutput = "Total expenditure in the month of Mar 2022 is $3300.0." + System.lineSeparator()
                + System.lineSeparator() + "BREAKDOWN OF EXPENSES:" + System.lineSeparator()
                + "-----------------------------------------------" + System.lineSeparator()
                + "FOOD:           0.0%" + System.lineSeparator()
                + "TRANSPORT:      0.0%" + System.lineSeparator()
                + "UTILITIES:      0.0%" + System.lineSeparator()
                + "PERSONAL:      ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ 100.0%" + System.lineSeparator()
                + "ENTERTAINMENT:  0.0%" + System.lineSeparator()
                + "OTHERS:         0.0%" + System.lineSeparator()
                + "-----------------------------------------------"
                ;
        assertEquals(expectedOutput, capturedOut.toString().trim());
    }

    /**
     * Asserts if user is able to input a case-insensitive Month.
     */
    @Test
    void calculateInputCommand_caseInsensitiveMonth_expectCorrectOutput() throws MindMyMoneyException {
        ExpenditureList expenditureTestList = new ExpenditureList();
        CreditCardList creditCardTestList = new CreditCardList();
        IncomeList incomeList = new IncomeList();
        User user = new User(expenditureTestList, creditCardTestList, incomeList);

        String inputString = "/e cash /c Personal /d Nike Shoes /a 0.5 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();
        inputString = "/e cash /c Personal /d Nike Shoes /a 0.10 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();

        setUp();
        new CalculateInputCommand("/epm mAr 2022", user).executeCommand();
        tearDown();
        String expectedOutput = "Total expenditure in the month of Mar 2022 is $0.6." + System.lineSeparator()
                + System.lineSeparator() + "BREAKDOWN OF EXPENSES:" + System.lineSeparator()
                + "-----------------------------------------------" + System.lineSeparator()
                + "FOOD:           0.0%" + System.lineSeparator()
                + "TRANSPORT:      0.0%" + System.lineSeparator()
                + "UTILITIES:      0.0%" + System.lineSeparator()
                + "PERSONAL:      ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ 100.0%" + System.lineSeparator()
                + "ENTERTAINMENT:  0.0%" + System.lineSeparator()
                + "OTHERS:         0.0%" + System.lineSeparator()
                + "-----------------------------------------------"
                ;
        assertEquals(expectedOutput, capturedOut.toString().trim());
    }

    /**
     * Asserts if user is able to use an incorrect flag.
     */
    @Test
    void calculateInputCommand_wrongFlag_expectException() throws MindMyMoneyException {
        ExpenditureList expenditureTestList = new ExpenditureList();
        CreditCardList creditCardTestList = new CreditCardList();
        IncomeList incomeList = new IncomeList();
        User user = new User(expenditureTestList, creditCardTestList, incomeList);

        String inputString = "/e cash /c Personal /d Nike Shoes /a 300 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();
        inputString = "/e cash /c Personal /d Nike Shoes /a 3000 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();

        assertThrows(MindMyMoneyException.class,
            () -> new CalculateInputCommand("/a Mar 2022", user).executeCommand());
    }

    /**
     * Asserts if user is able to input an empty flag.
     */
    @Test
    void calculateInputCommand_emptyFlag_expectException() throws MindMyMoneyException {
        ExpenditureList expenditureTestList = new ExpenditureList();
        CreditCardList creditCardTestList = new CreditCardList();
        IncomeList incomeList = new IncomeList();
        User user = new User(expenditureTestList, creditCardTestList, incomeList);

        String inputString = "/e cash /c Personal /d Nike Shoes /a 300 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();
        inputString = "/e cash /c Personal /d Nike Shoes /a 3000 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();

        assertThrows(MindMyMoneyException.class,
            () -> new CalculateInputCommand("Mar 2022", user).executeCommand());
    }

    /**
     * Asserts if user is able to input an improper input.
     */
    @Test
    void calculateInputCommand_wrongInput_expectException() throws MindMyMoneyException {
        ExpenditureList expenditureTestList = new ExpenditureList();
        CreditCardList creditCardTestList = new CreditCardList();
        IncomeList incomeList = new IncomeList();
        User user = new User(expenditureTestList, creditCardTestList, incomeList);

        String inputString = "/e cash /c Personal /d Nike Shoes /a 300 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();
        inputString = "/e cash /c Personal /d Nike Shoes /a 3000 /t 2022-03";
        new AddCommand(inputString, user).executeCommand();

        assertThrows(MindMyMoneyException.class,
            () -> new CalculateInputCommand("Mar 2022", user).executeCommand());
    }

    /**
     * Asserts if user is able to calculate from an empty list.
     */
    @Test
    void calculateInputCommand_emptyList_expectException() {
        ExpenditureList expenditureTestList = new ExpenditureList();
        CreditCardList creditCardTestList = new CreditCardList();
        IncomeList incomeList = new IncomeList();
        User user = new User(expenditureTestList, creditCardTestList, incomeList);

        assertThrows(MindMyMoneyException.class,
            () -> new CalculateInputCommand("Mar 2022", user).executeCommand());
    }

    public void tearDown() {
        System.setOut(stdout);
    }
}
