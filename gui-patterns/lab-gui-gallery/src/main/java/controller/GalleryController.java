package controller;


import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Gallery;
import model.Photo;
import util.PhotoDownloader;

import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class GalleryController {

    @FXML
    public TextField imageNameField;
    @FXML
    public ImageView imageView;
    public ListView<Photo> imagesListView;
    public TextField searchTextField;
    public Button searchButton;
    private Gallery galleryModel;

    @FXML
    public void initialize() {
        imagesListView.getSelectionModel().selectedItemProperty()
                        .addListener((obs, oldVal, newVal) -> {
                            if (oldVal != null) {
                                imageNameField.textProperty().unbindBidirectional(oldVal.nameProperty());
                                imageView.imageProperty().unbind();
                            }
                            if (newVal != null) {
                                imageNameField.textProperty().bindBidirectional(newVal.nameProperty());
                                imageView.imageProperty().bind(newVal.photoProperty());
                            }
                        });
        imagesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        imagesListView.setItems(gallery.getPhotos());
        imagesListView.getSelectionModel().select(0);
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        imageNameField.textProperty().bindBidirectional(selectedPhoto.nameProperty());
        imageView.imageProperty().bind(selectedPhoto.photoProperty());
    }

    @FXML
    public void searchButtonClicked(javafx.event.ActionEvent actionEvent) {
        final boolean[] first = {true};
        galleryModel.clear();

        PhotoDownloader photoDownloader = new PhotoDownloader();

        photoDownloader.searchForPhotos(searchTextField.getText())
                .subscribeOn(Schedulers.io())
                .subscribe(photo -> Platform.runLater( () -> {
                    galleryModel.addPhoto(photo);
                    if (first[0]) {
                        imagesListView.getSelectionModel().select(photo);
                        first[0] = false;
                    }
        }));
    }
}

