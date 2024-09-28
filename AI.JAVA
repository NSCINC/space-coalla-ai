import sqlite3
from openpyxl import load_workbook

# Estrutura para armazenar os dados dos investidores
class Investor:
    def __init__(self, name, email, phone_number):
        self.name = name
        self.email = email
        self.phone_number = phone_number

# Estrutura para armazenar os dados dos ativos
class Asset:
    def __init__(self, asset_name, asset_type, total_slots, annual_return):
        self.asset_name = asset_name
        self.asset_type = asset_type
        self.total_slots = total_slots
        self.annual_return = annual_return

# Estrutura para armazenar o investimento
class Investment:
    def __init__(self, investor_id, asset_id, invested_amount):
        self.investor_id = investor_id
        self.asset_id = asset_id
        self.invested_amount = invested_amount

# Função para inicializar o banco de dados e criar tabelas
def initialize_database():
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    # Criação das tabelas
    cursor.execute('''CREATE TABLE IF NOT EXISTS Investors (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        email TEXT NOT NULL,
                        phone_number TEXT NOT NULL);''')

    cursor.execute('''CREATE TABLE IF NOT EXISTS Assets (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        asset_name TEXT NOT NULL,
                        asset_type TEXT NOT NULL,
                        total_slots INTEGER NOT NULL,
                        annual_return REAL NOT NULL);''')

    cursor.execute('''CREATE TABLE IF NOT EXISTS Investments (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        investor_id INTEGER,
                        asset_id INTEGER,
                        invested_amount REAL,
                        FOREIGN KEY(investor_id) REFERENCES Investors(id),
                        FOREIGN KEY(asset_id) REFERENCES Assets(id));''')

    conn.commit()
    conn.close()

# Função para inserir investidores
def insert_investor(investor):
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    cursor.execute('''INSERT INTO Investors (name, email, phone_number)
                      VALUES (?, ?, ?);''', (investor.name, investor.email, investor.phone_number))

    conn.commit()
    conn.close()

# Função para inserir ativos
def insert_asset(asset):
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    cursor.execute('''INSERT INTO Assets (asset_name, asset_type, total_slots, annual_return)
                      VALUES (?, ?, ?, ?);''', (asset.asset_name, asset.asset_type, asset.total_slots, asset.annual_return))

    conn.commit()
    conn.close()

# Função para inserir investimentos
def insert_investment(investment):
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    cursor.execute('''INSERT INTO Investments (investor_id, asset_id, invested_amount)
                      VALUES (?, ?, ?);''', (investment.investor_id, investment.asset_id, investment.invested_amount))

    conn.commit()
    conn.close()

# Função para ler dados de Excel e inserir no banco de dados
def read_excel_data(file_path):
    workbook = load_workbook(filename=file_path)
    sheet = workbook.active

    # Exemplo: Lê os dados do Excel e insere os investidores no banco
    for row in sheet.iter_rows(min_row=2, values_only=True):
        # Considera que as colunas do Excel seguem a ordem: Nome, Email, Telefone
        name, email, phone_number = row
        investor = Investor(name, email, phone_number)
        insert_investor(investor)

# Exemplo de inicialização do banco e inserção de dados do Excel
if __name__ == '__main__':
    initialize_database()
    read_excel_data('investors_data.xlsx')  # Insira o caminho correto do seu arquivo Excel
import sqlite3
from openpyxl import load_workbook

# Estrutura para armazenar os dados dos investidores
class Investor:
    def __init__(self, name, email, phone_number):
        self.name = name
        self.email = email
        self.phone_number = phone_number

# Estrutura para armazenar os dados dos ativos
class Asset:
    def __init__(self, asset_name, asset_type, total_slots, annual_return):
        self.asset_name = asset_name
        self.asset_type = asset_type
        self.total_slots = total_slots
        self.annual_return = annual_return

# Estrutura para armazenar o investimento
class Investment:
    def __init__(self, investor_id, asset_id, invested_amount):
        self.investor_id = investor_id
        self.asset_id = asset_id
        self.invested_amount = invested_amount

# Função para inicializar o banco de dados e criar tabelas
def initialize_database():
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    # Criação das tabelas
    cursor.execute('''CREATE TABLE IF NOT EXISTS Investors (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        email TEXT NOT NULL,
                        phone_number TEXT NOT NULL);''')

    cursor.execute('''CREATE TABLE IF NOT EXISTS Assets (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        asset_name TEXT NOT NULL,
                        asset_type TEXT NOT NULL,
                        total_slots INTEGER NOT NULL,
                        annual_return REAL NOT NULL);''')

    cursor.execute('''CREATE TABLE IF NOT EXISTS Investments (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        investor_id INTEGER,
                        asset_id INTEGER,
                        invested_amount REAL,
                        FOREIGN KEY(investor_id) REFERENCES Investors(id),
                        FOREIGN KEY(asset_id) REFERENCES Assets(id));''')

    conn.commit()
    conn.close()

# Função para inserir investidores
def insert_investor(investor):
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    cursor.execute('''INSERT INTO Investors (name, email, phone_number)
                      VALUES (?, ?, ?);''', (investor.name, investor.email, investor.phone_number))

    conn.commit()
    conn.close()

# Função para inserir ativos
def insert_asset(asset):
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    cursor.execute('''INSERT INTO Assets (asset_name, asset_type, total_slots, annual_return)
                      VALUES (?, ?, ?, ?);''', (asset.asset_name, asset.asset_type, asset.total_slots, asset.annual_return))

    conn.commit()
    conn.close()

# Função para inserir investimentos
def insert_investment(investment):
    conn = sqlite3.connect('investments.db')
    cursor = conn.cursor()

    cursor.execute('''INSERT INTO Investments (investor_id, asset_id, invested_amount)
                      VALUES (?, ?, ?);''', (investment.investor_id, investment.asset_id, investment.invested_amount))

    conn.commit()
    conn.close()

# Função para ler dados de Excel e inserir no banco de dados
def read_excel_data(file_path):
    workbook = load_workbook(filename=file_path)
    sheet = workbook.active

    # Exemplo: Lê os dados do Excel e insere os investidores no banco
    for row in sheet.iter_rows(min_row=2, values_only=True):
        # Considera que as colunas do Excel seguem a ordem: Nome, Email, Telefone
        name, email, phone_number = row
        investor = Investor(name, email, phone_number)
        insert_investor(investor)

# Exemplo de inicialização do banco e inserção de dados do Excel
if __name__ == '__main__':
    initialize_database()
    read_excel_data('investors_data.xlsx')  # Insira o caminho correto do seu arquivo Excel
