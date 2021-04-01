package chess.dao;

import chess.domain.chessgame.ChessGame;

import java.sql.*;

public class ChessDAO {
    public Connection getConnection() {
        Connection con = null;
        String server = "localhost:13306"; // MySQL 서버 주소
        String database = "chess"; // MySQL DATABASE 이름
        String option = "?useSSL=false&serverTimezone=UTC";
        String userName = "root"; //  MySQL 서버 아이디
        String password = "root"; // MySQL 서버 비밀번호

        // 드라이버 로딩
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(" !! JDBC Driver load 오류: " + e.getMessage());
            e.printStackTrace();
        }

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + option, userName, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        return con;
    }

    // 드라이버 연결해제
    public void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    public void addGame(String gameId, ChessGame game) throws SQLException {
        String query = "INSERT INTO games VALUES (?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, gameId);
        pstmt.setString(2, game.boardForDAO());
        pstmt.setString(3, game.turnForDAO());
        pstmt.executeUpdate();
    }

    public ChessGame findGameById(String gameId) throws SQLException {
        String query = "SELECT * FROM games WHERE game_id = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, gameId);
        ResultSet rs = pstmt.executeQuery();

        if (!rs.next()) return null;

        return new ChessGame(
                rs.getString("pieces"),
                rs.getString("turn"));
    }

    public void deleteGameById(String gameId) throws SQLException {
        String query = "DELETE FROM games WHERE game_id = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, gameId);
        pstmt.executeUpdate();
    }

    public void updateGame(String gameId, ChessGame game) throws SQLException {
        String query = "UPDATE games SET pieces = ? , turn = ? WHERE game_id = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, game.boardForDAO());
        pstmt.setString(2, game.turnForDAO());
        pstmt.setString(3, gameId);
        pstmt.executeUpdate();
    }
}
