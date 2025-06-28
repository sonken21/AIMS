package com.aims.son.views.home;

import com.aims.son.controller.HomeController;
import com.aims.son.controller.ViewCartController;
import com.aims.son.entity.cart.Cart;
import com.aims.son.entity.media.Media;
import com.aims.son.exception.ViewCartException;
import com.aims.son.repositories.MediaRepository;
import com.aims.son.utils.Configs;
import com.aims.son.utils.Utils;
import com.aims.son.views.BaseForm;
import com.aims.son.views.cart.CartForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeForm extends BaseForm implements Initializable {

    public static Logger LOGGER = Utils.getLogger(HomeForm.class.getName());

    @FXML
    private Label numMediaInCart;

    @FXML
    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private HBox hboxMedia;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private ScrollPane scrollPaneMedia;

    @SuppressWarnings("rawtypes")
    private List homeItems;

    public HomeForm(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public Label getNumMediaCartLabel() {
        return this.numMediaInCart;
    }

    public HomeController getBController() {
        return (HomeController) super.getBController();
    }

    @Override
    public void show() {
        numMediaInCart.setText(String.valueOf(Cart.getCart().getListMedia().size()) + " media");
        super.show();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBController(new HomeController(new MediaRepository()));
        try {
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList();
            for (Object object : medium) {
                Media media = (Media) object;
                MediaForm m1 = new MediaForm(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }
        } catch (SQLException | IOException e) {
            LOGGER.info("Errors occurred: " + e.getMessage());  
            e.printStackTrace();
        }

        setupSearchButton();
        setupAimsImage();
        setupCartImage();
        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);
    }

    private void setupSearchButton() {
        splitMenuBtnSearch.setOnMouseClicked(e -> performSearch());
    }

    private void setupAimsImage() {
        aimsImage.setOnMouseClicked(e -> addMediaHome(this.homeItems));
    }

    private void setupCartImage() {
        cartImage.setOnMouseClicked(e -> {
            try {
                LOGGER.info("User clicked to view cart");
                CartForm cartScreen = new CartForm(this.stage, Configs.CART_SCREEN_PATH);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });


        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);


    }

    public void setImage() {
        // fix image path caused by fxml
        File file1 = new File(Configs.IMAGE_PATH_ICON + "/" + "Logo.png");
        Image img1 = new Image(file1.toURI().toString());
        aimsImage.setImage(img1);

        File file2 = new File(Configs.IMAGE_PATH_ICON + "/" + "cart.png");
        Image img2 = new Image(file2.toURI().toString());
        cartImage.setImage(img2);
    }

    @SuppressWarnings("rawtypes")
    public void addMediaHome(List items) {
        ArrayList mediaItems = (ArrayList) ((ArrayList) items).clone();
        hboxMedia.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });

        while (!mediaItems.isEmpty()) {
            hboxMedia.getChildren().forEach(node -> {
                int vid = hboxMedia.getChildren().indexOf(node);
                VBox vBox = (VBox) node;
                while (vBox.getChildren().size() < 5 && !mediaItems.isEmpty()) {
                    MediaForm media = (MediaForm) mediaItems.get(0);
                    vBox.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            });
            return;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void performSearch() {
        String query = textFieldSearch.getText().trim();
        if (query.isEmpty()) {
            // Nếu không có từ khóa, hiển thị tất cả media
            addMediaHome(this.homeItems);
            return;
        }

        // Lọc danh sách media theo từ khóa
        List filteredItems = new ArrayList<>();
        for (Object object : this.homeItems) {
            MediaForm media = (MediaForm) object;
            if (media.getMedia().getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(media);
            }
        }

        // Cập nhật giao diện với kết quả tìm kiếm
        addMediaHome(filteredItems);
        LOGGER.info("Search completed with query: " + query);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addMenuItem(int position, String text, MenuButton menuButton) {
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> filterMediaByCategory(text));
        menuButton.getItems().add(position, menuItem);
    }

    private void filterMediaByCategory(String category) {
        // empty home media
        hboxMedia.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });

        // filter only media with the chosen category
        List filteredItems = new ArrayList<>();
        homeItems.forEach(me -> {
            MediaForm media = (MediaForm) me;
            if (media.getMedia().getType().equalsIgnoreCase(category)) {
                filteredItems.add(media);
            }
        });

        // fill out the home with filtered media as category
        addMediaHome(filteredItems);
    }
}
