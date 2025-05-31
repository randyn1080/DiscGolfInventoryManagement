# Disc Golf Inventory Management System

## Project Description

A comprehensive REST API backend for managing disc golf disc inventory, featuring advanced search capabilities and complete CRUD operations. Built to solve the real-world problem of tracking disc golf discs with their specific flight characteristics and physical properties. The system enables efficient inventory management through sophisticated filtering and search functionality using multiple criteria including manufacturer, mold, flight numbers, and physical specifications.

## Technologies Used

* Java - version 24
* Javalin - version 5.0.1
* H2 Database - version 2.1.214
* Maven - version 3.8
* Jackson - version 2.14.0
* SLF4J - version 1.7.36

## Features

List of features ready and TODOs for future development
* Complete CRUD operations for disc management
* Advanced multi-criteria search functionality
* Filter by manufacturer, mold, color, and weight
* Filter by flight characteristics (speed, glide, turn, fade)
* Builder pattern implementation for flexible search queries
* Comprehensive error handling and logging
* RESTful API design with proper HTTP status codes
* Connection pooling for optimal database performance

To-do list:
* Add user authentication and authorization
* Implement disc condition tracking and history
* Add image upload functionality for disc photos
* Implement barcode scanning integration
* Add inventory location tracking
* Create batch import/export functionality
* Add disc value estimation and marketplace integration

## Getting Started

### Prerequisites
* Java 24 or higher
* Maven 3.6 or higher (for command line build)
* Git
* IntelliJ IDEA (recommended) or any Java IDE

### Clone the repository
git clone https://github.com/randyn1080/disc-inventory-management-system.git
cd disc-inventory-management-system

### Option 1: Run with IntelliJ IDEA (Recommended)
1. Open IntelliJ IDEA
2. File → Open → Select the cloned project folder
3. Wait for Maven import to complete (IntelliJ will automatically download dependencies)
4. Navigate to `src/main/java/com/discgolf/DiscGolfInventoryManagementApplication.java`
5. Right-click on `DiscGolfInventoryManagementApplication.java` → Run 'DiscGolfInventoryManagementApplication.main()'
6. Application will start on http://localhost:8080

### Option 2: Command Line (Windows)

mvn clean compile

mvn exec:java -Dexec.mainClass="com.discgolf.DiscGolfInventoryManagementApplication"

### Option 3: Command Line (macOS/Linux/Unix)
mvn clean compile

mvn exec:java -Dexec.mainClass="com.discgolf.DiscGolfInventoryManagementApplication"

> Note: Commands work on all platforms (Windows, macOS, Linux, Unix)
> Maven must be installed and configured in system PATH
> Java must be installed and JAVA_HOME configured
> H2 database files will be created automatically in h2/ directory

The application will start on http://localhost:8080
Press Ctrl+C in the terminal to stop the application

## Usage

Once the application is running, you can interact with the API using tools like Postman, curl, or any HTTP client. The API provides comprehensive disc inventory management with advanced search capabilities for disc golf enthusiasts and retailers.

### API Endpoints

**Create New Disc**
POST /discs
Content-Type: application/json

{
    "manufacturer": "Innova",
    "mold": "Destroyer",
    "color": "Star Blue",
    "weight": 175,
    "speed": 12,
    "glide": 5,
    "turn": -1,
    "fade": 3,
    "specialNotes": "First run Star plastic"
}

**Get All Discs**
GET /discs

**Get Disc by ID**
GET /discs/{discId}

**Update Disc**
PUT /discs/{discId}
Content-Type: application/json

{
    "discID": 1,
    "manufacturer": "Innova",
    "mold": "Destroyer",
    "color": "Champion Red",
    "weight": 175,
    "speed": 12,
    "glide": 5,
    "turn": -1,
    "fade": 3,
    "specialNotes": "Updated to Champion plastic"
}

**Delete Disc**
DELETE /discs/{discId}

**Search Discs (Query Parameters)**
GET /discs/search?manufacturer=Innova&speed=12&weight=175

**Advanced Search (POST with JSON)**
POST /discs/search
Content-Type: application/json

{
    "manufacturer": "Discraft",
    "speed": 5,
    "glide": 4,
    "turn": -1,
    "fade": 1
}

### Example Usage with curl

Add a new disc:
curl -X POST http://localhost:8080/discs -H "Content-Type: application/json" -d "{\"manufacturer\":\"MVP\",\"mold\":\"Volt\",\"color\":\"Neutron Green\",\"weight\":168,\"speed\":8,\"glide\":5,\"turn\":-1,\"fade\":2,\"specialNotes\":\"Cosmic Neutron plastic\"}"

Search for all Innova discs:
curl "http://localhost:8080/discs/search?manufacturer=Innova"

Get all discs:
curl http://localhost:8080/discs

### Search Capabilities

The API supports flexible searching by any combination of:
* **Manufacturer** - Brand name (Innova, Discraft, MVP, etc.)
* **Mold** - Disc model name (Destroyer, Buzzz, Volt, etc.)
* **Color** - Disc color or plastic type
* **Weight** - Exact weight in grams
* **Speed** - Flight speed rating (1-14)
* **Glide** - Glide rating (1-7)
* **Turn** - Turn rating (-5 to +1)
* **Fade** - Fade rating (0-5)

## Contributors

Randy Nava

## License

This project uses the following license: MIT License.
