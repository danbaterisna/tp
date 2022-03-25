package seedu.mindmymoney.command;

import seedu.mindmymoney.MindMyMoneyException;
import seedu.mindmymoney.data.ExpenditureList;
import seedu.mindmymoney.data.CreditCardList;

import static seedu.mindmymoney.constants.Flags.FLAG_OF_CREDIT_CARD;

/**
 * Represents the Delete command.
 */
public class DeleteCommand extends Command {
    private String input;
    public ExpenditureList expenditureList;
    public CreditCardList creditCardList;

    public DeleteCommand(String input, ExpenditureList expenditureList, CreditCardList creditCardList) {
        this.input = input;
        this.expenditureList = expenditureList;
        this.creditCardList = creditCardList;
    }

    /**
     * Indicates whether the program should exit.
     *
     * @return Indication on whether the program should exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    private boolean hasCreditCardFlag() {
        return input.contains(FLAG_OF_CREDIT_CARD);
    }

    /**
     * Checks if the position to delete is within the bounds of the array length.
     * @param positionToDelete position of index to delete.
     * @return true if position is within the bounds, false otherwise.
     */
    public boolean checkOutOfBounds(int positionToDelete) {
        return (positionToDelete + 1 <= 0 || positionToDelete + 1 > expenditureList.size());
    }

    /**
     * Checks if the position to delete is within the bounds of the credit card list.
     * @param positionToDelete position of index to delete.
     * @return true if position is within the bounds, false otherwise.
     */
    public boolean checkCreditCardListBounds(int positionToDelete) {
        return (positionToDelete + 1 <= 0 || positionToDelete + 1 > creditCardList.size());
    }

    /**
     * Removes the item from the expenditure list.
     */
    public void deleteExpenditure() throws MindMyMoneyException {
        try {
            if (expenditureList.isEmpty()) {
                throw new MindMyMoneyException(System.lineSeparator()
                        + "Please add something to your expenditure list first:)"
                        + System.lineSeparator());
            }
            String[] splitMessage = input.split(" ");
            if (splitMessage.length != 2) {
                throw new MindMyMoneyException(System.lineSeparator() + "Please input a number\n"
                        + "For eg. 'delete 2'\n");
            }
            String getNumber = splitMessage[1];
            int positionToDelete = Integer.parseInt(getNumber) - 1;
            if (checkOutOfBounds(positionToDelete)) {
                throw new MindMyMoneyException("Please input a valid index");
            } else {
                assert positionToDelete >= 0 : "Index should always be >= 0";
                System.out.println("I have removed "
                        + expenditureList.get(positionToDelete).getDescription()
                        + " of $" + expenditureList.get(positionToDelete).getAmount()
                        + " from the account" + System.lineSeparator());
                expenditureList.delete(positionToDelete);
            }
        } catch (NumberFormatException e) {
            throw new MindMyMoneyException("INDEX must be a number");
        }
    }

    /**
     * Removes a credit card from user's list of credit card(s).
     */
    public void deleteCreditCard() throws MindMyMoneyException {
        try {
            if (creditCardList.isEmpty()) {
                throw new MindMyMoneyException(System.lineSeparator()
                        + "Please add something to your credit card list first:)"
                        + System.lineSeparator());
            }
            String[] splitMessage = input.split(" ");
            if (splitMessage.length != 3) {
                throw new MindMyMoneyException(System.lineSeparator() + "Please input a number\n"
                        + "For eg. 'delete /cc 2' to remove the second credit card on your list.\n");
            }
            String getNumber = splitMessage[2];
            int positionToDelete = Integer.parseInt(getNumber) - 1;
            if (checkCreditCardListBounds(positionToDelete)) {
                throw new MindMyMoneyException("Please input a valid index");
            } else {
                assert positionToDelete >= 0 : "Index should always be >= 0";
                System.out.println("I have removed "
                        + creditCardList.get(positionToDelete).getNameOfCard()
                        + " from your list of credit card(s)." + System.lineSeparator());
                creditCardList.delete(positionToDelete);
            }
        } catch (NumberFormatException e) {
            throw new MindMyMoneyException("INDEX must be a number");
        }
    }

    public void executeCommand() throws MindMyMoneyException {
        if (hasCreditCardFlag()) {
            deleteCreditCard();
        } else {
            deleteExpenditure();
        }
    }
}
