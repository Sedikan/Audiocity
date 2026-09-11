package com.example.audiocitylibrary;



        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.layout.HBox;
        import javafx.scene.control.TableColumn;
        import javafx.stage.FileChooser;

        import java.io.File;
        import java.net.URL;
        import java.sql.*;
        import java.time.LocalDate;
        import java.util.ResourceBundle;

public class ShopController {

    private int userPermissions = LoginController.getUsersPermissions();

    public void setUserPermissions(int userPermissions) {
        this.userPermissions = userPermissions;

        // Check userPermissions and hide/show the button accordingly
        if (userPermissions != 1) {
            setShopFirstBtnAddVisibility(false);
        } else {
            setShopFirstBtnAddVisibility(true);
        }
    }
    public void setShopFirstBtnAddVisibility(boolean isVisible) {
        if (shop_first_btn_add != null) {
            shop_first_btn_add.setVisible(isVisible);
        }
    }
    public Button getShopFirstBtnAdd() {
        return shop_first_btn_add;
    }
    @FXML
    private Button shop_first_btn_add;

    @FXML
    private Button shop_first_btn_buy;

    // ... existing code

    public Button getShopFirstBtnBuy() {
        return shop_first_btn_buy;
    }
    @FXML
    private HBox firstWindow;

    @FXML
    private BorderPane add_window;

    @FXML
    private BorderPane buy_window;

    @FXML
    private BorderPane cart_window;

    @FXML
    private HBox first_window;

    @FXML
    private Button cart_btn_buy;

    @FXML
    private Button cart_btn_purchase;

    @FXML
    private TableView<shopDane> cart_tableView;

    @FXML
    private TableColumn<shopDane, String> cart_col_album;

    @FXML
    private TableColumn<shopDane, String> cart_col_artist;

    @FXML
    private TableColumn<shopDane, Integer> cart_col_prize;

    @FXML
    private TableColumn<shopDane, Integer> cart_col_quantity;

    @FXML
    private TextField cart_sum_prize;

    @FXML
    private TextField shop_add_album_name;

    @FXML
    private TextField shop_add_artist;

    @FXML
    private Button shop_add_btn_add1;
    @FXML
    private Button shop_add_go_to_btn_add;

    @FXML
    private ImageView shop_add_btn_album_image;

    @FXML
    private Button shop_add_btn_buy;

    @FXML
    private Button shop_add_btn_clear;

    @FXML
    private Button shop_add_btn_delete;

    @FXML
    private Button shop_add_btn_insert_photo;

    @FXML
    private Button shop_add_btn_update;

    @FXML
    private TableView<shopDane> shop_add_tableView;

    @FXML
    private TableColumn<shopDane, String> shop_add_col_album_name;

    @FXML
    private TableColumn<shopDane, String> shop_add_col_artist;

    @FXML
    private TableColumn<shopDane, Integer> shop_add_col_prize;

    @FXML
    private TableColumn<shopDane, Integer> shop_add_col_quantity_songs;


    @FXML
    private TextField shop_add_prize;

    @FXML
    private TextField shop_add_quantity_songs;

    @FXML
    private TextField shop_add_search;

    @FXML
    private TextField shop_buy_album;

    @FXML
    private TextField shop_buy_artist;

    @FXML
    private Button shop_buy_btn_add;

    @FXML
    private Button shop_buy_btn_add_to_cart;

    @FXML
    private Button shop_buy_btn_buy;

    @FXML
    private Button shop_buy_cart;

    @FXML
    private TableView<shopDane> shop_buy_tableView;

    @FXML
    private TableColumn<shopDane, String> shop_buy_col_album;

    @FXML
    private TableColumn<shopDane, String> shop_buy_col_artist;

    @FXML
    private TableColumn<shopDane, Integer> shop_buy_col_prize;

    @FXML
    private TableColumn<shopDane, Integer> shop_buy_col_quantity_songs;

    @FXML
    private ImageView shop_buy_image_album;

    @FXML
    private TextField shop_buy_prize;

    @FXML
    private Spinner<?> shop_buy_quantity_albums;

    @FXML
    private TextField shop_buy_quantity_songs;

    @FXML
    private TextField shop_buy_search;

    private Image image;

    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet result;

    private Statement statement;

    int userPermission = LoginController.getUprawnieniaZalogowanegoUzytkownika();

    private ObservableList<shopDane> addlistAlbum(){
        ObservableList<shopDane> listAlbum = FXCollections.observableArrayList();
        String sql = "SELECT album.nazwaAlbumu, autor.nazwaAutora, COUNT(utwor.idUtworu) AS iloscUtworow," +
                "album.cena, album.image FROM album JOIN autor ON album.idAutora = autor.idAutora"+
                " JOIN utwor ON utwor.idAlbumu = album.idAlbum GROUP BY album.idAlbum;";
        try (Connection connection = databaseHandler.connectDb();
             PreparedStatement prepare = connection.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                shopDane sD = new shopDane(
                        result.getString("nazwaAlbumu"),
                        result.getString("nazwaAutora"),
                        result.getInt("iloscUtworow"),
                        result.getInt("cena"),
                        result.getString("image"));
                listAlbum.add(sD);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAlbum;
    }

    private ObservableList<shopDane> addlistAlbumD;

    private ObservableList<shopDane> listAdminShop;

    private ObservableList<shopDane> koszyk = FXCollections.observableArrayList();


    public void addlistAlbumSHOP() {
        addlistAlbumD = addlistAlbum();

        shop_buy_col_album.setCellValueFactory(cellData -> cellData.getValue().nazwaAlbumuProperty());
        shop_buy_col_artist.setCellValueFactory(cellData -> cellData.getValue().artystaProperty());
        shop_buy_col_quantity_songs.setCellValueFactory(cellData -> cellData.getValue().iloscUtworowProperty().asObject());
        shop_buy_col_prize.setCellValueFactory(cellData -> cellData.getValue().cenaAlbumuProperty().asObject());

        shop_buy_tableView.setItems(addlistAlbumD);
    }


    public void listAlbumSelect() {
        shopDane shopBuy = shop_buy_tableView.getSelectionModel().getSelectedItem();
        int num = shop_buy_tableView.getSelectionModel().getSelectedIndex();
        if((num-1)<-1){return;}
        shop_buy_album.textProperty().bind(shopBuy.nazwaAlbumuProperty());//setText(String.valueOf(shopBuy.nazwaAlbumuProperty()));
        shop_buy_artist.textProperty().bind(shopBuy.artystaProperty());
        shop_buy_quantity_songs.textProperty().bind(shopBuy.iloscUtworowProperty().asString());
        shop_buy_prize.textProperty().bind(shopBuy.cenaAlbumuProperty().asString());

        //String imgPath = shopBuy.imageProperty().get();
        String url = "file:" + shopBuy.imageProperty().get();//getClass().getResource(imgPath).toExternalForm();
        System.out.println(url); /// trzeba ogarnąć jaką ścieżkę bedzie widział :)
        image = new Image(url,150,150,false,true);
        shop_buy_image_album.setImage(image);
    }
    public void ShopAdmin() {
        listAdminShop = addlistAlbum();

        shop_add_col_album_name.setCellValueFactory(cellData -> cellData.getValue().nazwaAlbumuProperty());
        shop_add_col_artist.setCellValueFactory(cellData -> cellData.getValue().artystaProperty());
        shop_add_col_quantity_songs.setCellValueFactory(cellData -> cellData.getValue().iloscUtworowProperty().asObject());
        shop_add_col_prize.setCellValueFactory(cellData -> cellData.getValue().cenaAlbumuProperty().asObject());

        shop_add_tableView.setItems(listAdminShop);
    }
    public void ShopAdminSelect() {
        shopDane shopBuy = shop_add_tableView.getSelectionModel().getSelectedItem();
        int num = shop_add_tableView.getSelectionModel().getSelectedIndex();
        if((num-1)<-1){return;}
        shop_add_album_name.textProperty().bind(shopBuy.nazwaAlbumuProperty());//setText(String.valueOf(shopBuy.nazwaAlbumuProperty()));
        shop_add_artist.textProperty().bind(shopBuy.artystaProperty());
        shop_add_prize.textProperty().bind(shopBuy.cenaAlbumuProperty().asString());

        //String imgPath = shopBuy.imageProperty().get();
        String url = "file:" + shopBuy.imageProperty().get();//getClass().getResource(imgPath).toExternalForm();
        System.out.println(url); /// trzeba ogarnąć jaką ścieżkę bedzie widział :)
        image = new Image(url,150,150,false,true);
        shop_add_btn_album_image.setImage(image);
    }

    public void shopAdminInsertImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","*jpg", "*png"));
        File file = open.showOpenDialog(add_window.getScene().getWindow());
        if(file != null){
            image = new Image(file.toURI().toString(), 150, 150, false, true);
            shop_add_btn_album_image.setImage(image);
            getData_Image.path = file.getPath(); //getAbsolutePath?
        }
    }

    public void addAlbumAdmin(){
        String insertDataAutor = "INSERT INTO `autor` (`idAutora`, `nazwaAutora`) VALUES (NULL,?)";
        String insertDataAlbum = "INSERT INTO `album` (`idAlbum`, `nazwaAlbumu`, `idAutora`, `cena`, `image`) VALUES (NULL, ?, ?, ?, ?)";
        // check if Autor exists
        //     yes- dodawaj id jego i nowy album
        //     no - dodaj autora i dopiero jego id i nowy album

        try{
            connection = databaseHandler.connectDb();

            Alert alert;
            if(shop_add_album_name.getText().isEmpty() || shop_add_artist.getText().isEmpty() || shop_add_prize.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Pola nie mogą być puste");
                alert.showAndWait();
            }else {
                String checkAlbum = "SELECT nazwaAlbumu FROM album WHERE nazwaAlbumu = '"+ shop_add_album_name.getText() +"'";
                String checkArtist = "SELECT idAutora FROM autor WHERE nazwaAutora = '"+ shop_add_artist.getText() +"'";
                statement = connection.createStatement();
                result = statement.executeQuery(checkArtist);
                if(result.next()){
                    result = statement.executeQuery(checkAlbum);
                    if(result.next()){
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd");
                        alert.setHeaderText(null);
                        alert.setContentText("Taki album już istnieje");
                        alert.showAndWait();
                    }else{
                        String id_Autor = "SELECT idAutora FROM autor WHERE nazwaAutora = '"+ shop_add_artist.getText() +"'";
                        result = statement.executeQuery(id_Autor);
                        if(result.next()){
                            Integer temp = result.getInt("idAutora");
                            prepare = connection.prepareStatement(insertDataAlbum);
                            prepare.setString(1, shop_add_album_name.getText());
                            prepare.setInt(2, temp);
                            prepare.setInt(3, Integer.parseInt(shop_add_prize.getText()));
                            String url = getData_Image.path;
                            url = url.replace("\\","\\\\");
                            prepare.setString(4,url);
                            prepare.executeUpdate();   }

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText(null);
                        alert.setContentText("Dodano nowy Album");
                        alert.showAndWait();
                    }
                }else{
                    prepare = connection.prepareStatement(insertDataAutor);
                    prepare.setString(1, shop_add_artist.getText());
                    prepare.executeUpdate();
                    String id_Autor = "SELECT idAutora FROM autor WHERE nazwaAutora = '"+ shop_add_artist.getText() +"'";
                    result = statement.executeQuery(id_Autor);
                    if(result.next()){
                        Integer temp = result.getInt("idAutora");
                        prepare = connection.prepareStatement(insertDataAlbum);
                        prepare.setString(1, shop_add_album_name.getText());
                        prepare.setInt(2,  temp);
                        prepare.setInt(3, Integer.parseInt(shop_add_prize.getText()));
                        String url = getData_Image.path;
                        url = url.replace("\\","\\\\");
                        prepare.setString(4,url);
                        prepare.executeUpdate();      }

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setHeaderText(null);
                    alert.setContentText("Dodano nowego Artystę oraz Album");
                    alert.showAndWait();
                }


            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void clearAdmin(){
        shop_add_album_name.textProperty().unbind();
        shop_add_artist.textProperty().unbind();
        shop_add_prize.textProperty().unbind();

        shop_add_album_name.setText("");
        shop_add_artist.setText("");
        shop_add_prize.setText("");
    }

    public void setShop_add_btn_update(){
        shop_add_album_name.textProperty().unbind();
        shop_add_artist.textProperty().unbind();
        shop_add_prize.textProperty().unbind();

        shop_add_album_name.setText("");
        shop_add_artist.setText("");
        shop_add_prize.setText("");
        ShopAdmin();
    }

    private ObservableList<shopDane> koszyczek(){
        Alert alert;
        ObservableList<shopDane> koszList = FXCollections.observableArrayList();
        if (shop_buy_album.getText().isEmpty() || shop_buy_artist.getText().isEmpty() || shop_buy_quantity_songs.getText().isEmpty() || shop_buy_prize.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz jakiś album. Pola nie mogą być puste.");
            alert.showAndWait();
        } else {
            String album = shop_buy_album.getText();
            String artist = shop_buy_artist.getText();
            Integer quantity = Integer.parseInt(shop_buy_quantity_songs.getText());
            Integer prize = Integer.parseInt(shop_buy_prize.getText());
            shopDane sD = new shopDane(album, artist, quantity, prize, "");
            koszList.add(sD);
        }
        return koszList;
    }


    private void dodajDoKoszyka() {
        Alert alert;
        if (shop_buy_album.getText().isEmpty() || shop_buy_artist.getText().isEmpty() || shop_buy_quantity_songs.getText().isEmpty() || shop_buy_prize.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz jakiś album. Pola nie mogą być puste.");
            alert.showAndWait();
        } else {
            shopDane shopBuy = shop_buy_tableView.getSelectionModel().getSelectedItem();
            if (shopBuy != null) {
                boolean alreadyInCart = false;
                for (shopDane item : koszyk) {
                    if (item.equals(shopBuy)) {
                        alreadyInCart = true;
                        break;
                    }
                }

                if (alreadyInCart) {
                    for (shopDane item : koszyk) {
                        if (item.equals(shopBuy)) {
                            item.setQuantity(item.getQuantity() + shopBuy.getQuantity());
                            break;
                        }
                    }
                } else {
                    shopBuy.setQuantity(1);
                    koszyk.add(shopBuy);
                }
            }
        }
    }



    public void setShop_buy_btn_add_to_cart() {
        dodajDoKoszyka();
        //koszyk = koszyczek();

        cart_col_album.setCellValueFactory(cellData -> cellData.getValue().nazwaAlbumuProperty());
        cart_col_artist.setCellValueFactory(cellData -> cellData.getValue().artystaProperty());
        cart_col_quantity.setCellValueFactory(cellData -> cellData.getValue().iloscUtworowProperty().asObject());
        cart_col_prize.setCellValueFactory(cellData -> cellData.getValue().cenaAlbumuProperty().asObject());

        cart_tableView.setItems(koszyk);/*
        Alert alert;
        if (shop_buy_album.getText().isEmpty() || shop_buy_artist.getText().isEmpty() || shop_buy_quantity_songs.getText().isEmpty() || shop_buy_prize.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz jakiś album. Pola nie mogą być puste.");
            alert.showAndWait();
        } else {
            String album = shop_buy_album.getText();
            String artist = shop_buy_artist.getText();
            String quantity = shop_buy_quantity_songs.getText();
            String prize = shop_buy_prize.getText();

            shopDane newCartItem = new shopDane(album, artist, Integer.parseInt(quantity), Integer.parseInt(prize), "");

            // Dodanie nowego wiersza do koszyka
            koszyk.add(newCartItem);

            // Odświeżenie widoku TableView
            cart_tableView.refresh();

            cart_tableView.setItems(koszyk);

            // Opcjonalnie można wypisać zawartość koszyka
            System.out.println("Dodano do koszyka: " + newCartItem);
        }*/
    }
    @FXML
    void get_cart_sum_prize() {
        int totalPrice = 0;
        for (Object item : cart_tableView.getItems()) {
            if (item instanceof shopDane) {
                int price = ((shopDane) item).cenaAlbumuProperty().get();
                totalPrice += price;
            }
        }
        cart_sum_prize.setText(Integer.toString(totalPrice));
    }


    @FXML
    void kupno(ActionEvent event) {
        Alert alert;
        ObservableList<shopDane> selectedAlbums = cart_tableView.getItems();

        if (selectedAlbums.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Nie wybrano żadnego albumu.");
            alert.showAndWait();
        } else {
            try {
                int idUzytkownika = LoginController.getIdZalogowanegoUzytkownika();

                int sumaCenAlbumow = Integer.parseInt(cart_sum_prize.getText());

                int aktualneSaldo = getAktualneSaldoUzytkownika(idUzytkownika);

                int noweSaldo = aktualneSaldo - sumaCenAlbumow;

                updateSaldoUzytkownika(idUzytkownika, noweSaldo);

                for (shopDane selectedAlbum : selectedAlbums) {
                    int idAlbumu = getIdAlbumu(selectedAlbum.nazwaAlbumuProperty().get());
                    insertIntoZakupioneAlbumy(idUzytkownika, idAlbumu);
                    int idZamowienia = insertIntoOrders(idUzytkownika, LocalDate.now());
                    insertIntoAlbumOrder(idZamowienia, idAlbumu);
                }

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Kupiono");
                alert.setHeaderText(null);
                alert.setContentText("Zakupy udane :)");
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getAktualneSaldoUzytkownika(int idUzytkownika) throws SQLException {
        int saldo = 0;
        String sql = "SELECT saldo FROM users WHERE idUzytkownika = ?";

        try (Connection connection = databaseHandler.connectDb();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUzytkownika);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    saldo = resultSet.getInt("saldo");
                }
            }
        }

        return saldo;
    }

    private void updateSaldoUzytkownika(int idUzytkownika, int noweSaldo) throws SQLException {
        String sql = "UPDATE users SET saldo = ? WHERE idUzytkownika = ?";

        try (Connection connection = databaseHandler.connectDb();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, noweSaldo);
            statement.setInt(2, idUzytkownika);
            statement.executeUpdate();
        }
    }


    // Metoda do wstawiania rekordu do tabeli orders
   /* private void insertIntoOrders(int idUser, LocalDate dataZamowienia) throws SQLException {
        String sql = "INSERT INTO orders (idUser, dataZamowienia) VALUES (?, ?)";

        connection = databaseHandler.connectDb();
        prepare = connection.prepareStatement(sql);
        prepare.setInt(1, idUser);
        prepare.setDate(2, Date.valueOf(dataZamowienia));

        prepare.executeUpdate();
    }*/



    // Metoda do pobierania identyfikatora albumu na podstawie jego nazwy
    private int getIdAlbumu(String nazwaAlbumu) throws SQLException {
        int idAlbumu = -1; // Wartość domyślna w przypadku niepowodzenia

        String sql = "SELECT idAlbum FROM album WHERE nazwaAlbumu = ?";

        connection = databaseHandler.connectDb();
        prepare = connection.prepareStatement(sql);
        prepare.setString(1, nazwaAlbumu);
        result = prepare.executeQuery();

        if (result.next()) {
            idAlbumu = result.getInt("idAlbum");
        }

        return idAlbumu;
    }


    // Metoda do wstawiania rekordu do tabeli zakupioneAlbumy
    private int insertIntoOrders(int idUzytkownika, LocalDate dataZamowienia) throws SQLException {
        String sql = "INSERT INTO orders (idUser, dataZamowienia) VALUES (?, ?)";
        connection = databaseHandler.connectDb();
        prepare = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepare.setInt(1, idUzytkownika);
        prepare.setDate(2, Date.valueOf(dataZamowienia));

        prepare.executeUpdate();

        ResultSet generatedKeys = prepare.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1); // Pobierz wygenerowane id zamówienia
        } else {
            throw new SQLException("Nie udało się pobrać wygenerowanego id zamówienia.");
        }
    }


    private void insertIntoZakupioneAlbumy(int idUzytkownika, int idAlbumu) throws SQLException {
        String sql = "INSERT INTO zakupioneAlbumy (idUzytkownika, id_albumu) VALUES (?, ?)";

        connection = databaseHandler.connectDb();
        prepare = connection.prepareStatement(sql);
        prepare.setInt(1, idUzytkownika);
        prepare.setInt(2, idAlbumu);

        prepare.executeUpdate();
    }


    // Metoda do wstawiania rekordu do tabeli album_order
    private void insertIntoAlbumOrder(int idZamowienia, int idAlbumu) throws SQLException {
        String sql = "INSERT INTO album_order (id_zamowienia, id_albumu) VALUES (?,?)";

        connection = databaseHandler.connectDb();
        prepare = connection.prepareStatement(sql);
        prepare.setInt(1, idZamowienia);
        prepare.setInt(2, idAlbumu);

        prepare.executeUpdate();
    }



    @FXML
    void shop_first_btn_add(ActionEvent event) {
        if (userPermission == 1){
        buy_window.setVisible(false);
        add_window.setVisible(true);
        cart_window.setVisible(false);
        first_window.setVisible(false);
        ShopAdmin();}else{
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("To add album, you have to be an admin");
            alert.showAndWait();
        }
    }

    @FXML
    void shop_first_btn_buy(ActionEvent event) {
        buy_window.setVisible(true);
        add_window.setVisible(false);
        cart_window.setVisible(false);
        first_window.setVisible(false);
        addlistAlbumSHOP();if (userPermission == 0){
            shop_buy_btn_add.setVisible(false);
            shop_buy_btn_buy.setVisible(false);
        }
    }

    public HBox getFirstWindow() {
        return first_window;
    }

    public void switch_form(ActionEvent event) {
        if(event.getSource() == shop_buy_btn_add){

            buy_window.setVisible(false);
            add_window.setVisible(true);
            cart_window.setVisible(false);
            first_window.setVisible(false);
            ShopAdmin();
        }else if(event.getSource() == shop_add_go_to_btn_add){
            buy_window.setVisible(false);
            add_window.setVisible(true);
            cart_window.setVisible(false);
            first_window.setVisible(false);
            ShopAdmin();
        }else if(event.getSource() == shop_buy_btn_buy){
            buy_window.setVisible(true);
            add_window.setVisible(false);
            cart_window.setVisible(false);
            first_window.setVisible(false);
            addlistAlbumSHOP();
        }else if(event.getSource() == shop_add_btn_buy){
            buy_window.setVisible(true);
            add_window.setVisible(false);
            cart_window.setVisible(false);
            first_window.setVisible(false);
            addlistAlbumSHOP();
        }else if(event.getSource() == shop_buy_cart){
            buy_window.setVisible(false);
            add_window.setVisible(false);
            cart_window.setVisible(true);
            first_window.setVisible(false);
            setShop_buy_btn_add_to_cart();
            get_cart_sum_prize();
        }else if(event.getSource() == cart_btn_buy){
            buy_window.setVisible(true);
            add_window.setVisible(false);
            cart_window.setVisible(false);
            first_window.setVisible(false);
            addlistAlbumSHOP();
        }
    }


}

