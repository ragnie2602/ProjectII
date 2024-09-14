package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Resources.Tools;

public class AuthQuery {
    public static boolean changeInformation(Account account) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement(
                        "UPDATE ACCOUNT SET NAME = ?, DOB = ?, PHONENUMBER = ?, EMAIL = ?, SCHOOL = ?, CLASS = ?, [IMAGE] = ? WHERE ID = ?");

                preparedStatement.setString(1, account.getName());
                preparedStatement.setDate(2, account.getDob());
                preparedStatement.setString(3, account.getPhoneNumber());
                preparedStatement.setString(4, account.getEmail());
                preparedStatement.setString(5, account.getSchool());
                preparedStatement.setString(6, account.getclass());
                preparedStatement.setBytes(7,
                        account.getImage() == null ? null : Tools.imageToBytes(account.getImage()));
                preparedStatement.setInt(8, account.getId());

                return preparedStatement.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean changePassword(int userId, String password) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database
                        .prepareStatement("UPDATE [LOGIN] SET [PASSWORD] = ? WHERE ID = ?");

                preparedStatement.setString(1, password);
                preparedStatement.setInt(2, userId);

                return preparedStatement.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean checkPassword(int userId, String password) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database
                        .prepareStatement("SELECT [PASSWORD] FROM LOGIN WHERE ID = ?");

                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return password.equals(resultSet.getString(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static Account getUser(int userId) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database
                        .prepareStatement("SELECT * FROM ACCOUNT WHERE ID = ?");

                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new Account(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getDate(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            Tools.BytesToImage(resultSet.getBytes(9)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Account login(String username, String password) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database
                        .prepareStatement("SELECT ID FROM [LOGIN] WHERE USERNAME = ? AND [PASSWORD] = ?");

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM ACCOUNT WHERE ID = ?");

                    preparedStatement.setInt(1, resultSet.getInt(1));

                    resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        return new Account(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
                                resultSet.getDate(4), resultSet.getString(5), resultSet.getString(6),
                                resultSet.getString(7), resultSet.getString(8),
                                Tools.BytesToImage(resultSet.getBytes(9)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static int signUp(Account account, String username, String password) {
        if (DBConnection.database != null) {
            try {
                // Register username and password to get ID
                PreparedStatement preparedStatement;
                try {
                    preparedStatement = DBConnection.database
                            .prepareStatement("INSERT INTO [LOGIN] VALUES(?, ?)");

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    return e.getErrorCode();
                }

                // Get ID
                preparedStatement = DBConnection.database
                        .prepareStatement("SELECT ID FROM [LOGIN] WHERE USERNAME = ? AND [PASSWORD] = ?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    try {
                        int id = resultSet.getInt(1);

                        preparedStatement = DBConnection.database
                                .prepareStatement("INSERT INTO ACCOUNT VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

                        preparedStatement.setInt(1, id);
                        preparedStatement.setInt(2, account.getRole());
                        preparedStatement.setString(3, account.getName());
                        preparedStatement.setDate(4, account.getDob());
                        preparedStatement.setString(5, account.getPhoneNumber());
                        preparedStatement.setString(6, account.getEmail());
                        preparedStatement.setString(7, account.getSchool());
                        preparedStatement.setString(8, account.getclass());
                        preparedStatement.setBytes(9, Tools.imageToBytes(account.getImage()));

                        preparedStatement.executeUpdate();
                    } catch (Exception e) {
                        int id = resultSet.getInt(1);

                        preparedStatement = DBConnection.database.prepareStatement("DELETE FROM [LOGIN] WHERE ID = ?");
                        preparedStatement.setInt(1, id);

                        preparedStatement.executeUpdate();

                        e.printStackTrace();
                    }
                }

                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return -1;
    }
}
