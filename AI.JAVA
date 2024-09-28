import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class Investor {
    private String name;
    private String email;
    private String phoneNumber;

    public Investor(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

class Asset {
    private String assetName;
    private String assetType;
    private int totalSlots;
    private double annualReturn;

    public Asset(String assetName, String assetType, int totalSlots, double annualReturn) {
        this.assetName = assetName;
        this.assetType = assetType;
        this.totalSlots = totalSlots;
        this.annualReturn = annualReturn;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public double getAnnualReturn() {
        return annualReturn;
    }
}

class Investment {
    private int investorId;
    private int assetId;
    private double investedAmount;

    public Investment(int investorId, int assetId, double investedAmount) {
        this.investorId = investorId;
        this.assetId = assetId;
        this.investedAmount = investedAmount;
    }

    public int getInvestorId() {
        return investorId;
    }

    public int getAssetId() {
        return assetId;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }
}

public class InvestmentManager {

    // Função para inicializar o banco de dados e criar tabelas
    public static void initializeDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:investments.db")) {
            Statement stmt = conn.createStatement();

            // Criação das tabelas
            stmt.execute("CREATE TABLE IF NOT EXISTS Investors (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "phone_number TEXT NOT NULL);");

            stmt.execute("CREATE TABLE IF NOT EXISTS Assets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "asset_name TEXT NOT NULL, " +
                    "asset_type TEXT NOT NULL, " +
                    "total_slots INTEGER NOT NULL, " +
                    "annual_return REAL NOT NULL);");

            stmt.execute("CREATE TABLE IF NOT EXISTS Investments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "investor_id INTEGER, " +
                    "asset_id INTEGER, " +
                    "invested_amount REAL, " +
                    "FOREIGN KEY(investor_id) REFERENCES Investors(id), " +
                    "FOREIGN KEY(asset_id) REFERENCES Assets(id));");
        }
    }

    // Função para inserir investidores
    public static void insertInvestor(Investor investor) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:investments.db")) {
            String query = "INSERT INTO Investors (name, email, phone_number) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, investor.getName());
            pstmt.setString(2, investor.getEmail());
            pstmt.setString(3, investor.getPhoneNumber());
            pstmt.executeUpdate();
        }
    }

    // Função para inserir ativos
    public static void insertAsset(Asset asset) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:investments.db")) {
            String query = "INSERT INTO Assets (asset_name, asset_type, total_slots, annual_return) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, asset.getAssetName());
            pstmt.setString(2, asset.getAssetType());
            pstmt.setInt(3, asset.getTotalSlots());
            pstmt.setDouble(4, asset.getAnnualReturn());
            pstmt.executeUpdate();
        }
    }

    // Função para inserir investimentos
    public static void insertInvestment(Investment investment) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:investments.db")) {
            String query = "INSERT INTO Investments (investor_id, asset_id, invested_amount) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, investment.getInvestorId());
            pstmt.setInt(2, investment.getAssetId());
            pstmt.setDouble(3, investment.getInvestedAmount());
            pstmt.executeUpdate();
        }
    }

    // Função para ler dados de Excel e inserir no banco de dados
    public static void readExcelData(String filePath) throws IOException, SQLException {
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Pular a primeira linha (cabeçalho)

                String name = row.getCell(0).getStringCellValue();
                String email = row.getCell(1).getStringCellValue();
                String phoneNumber = row.getCell(2).getStringCellValue();

                Investor investor = new Investor(name, email, phoneNumber);
                insertInvestor(investor);
            }
        }
    }

    public static void main(String[] args) {
        try {
            initializeDatabase();
            readExcelData("investors_data.xlsx");  // Coloque o caminho do seu arquivo Excel
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
