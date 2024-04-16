package com.tallervehiculos.uth.views.registrodevehículo;

import com.tallervehiculos.uth.data.controller.OrdenVehiculos_Interactor;
import com.tallervehiculos.uth.data.controller.OrdenVehiculos_InteractorImp;
import com.tallervehiculos.uth.data.entity.Vehiculo;
import com.tallervehiculos.uth.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Registro de Vehículo")
@Route(value = "registro-vehiculo/:vehiculoID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class RegistrodeVehículoView extends Div implements BeforeEnterObserver, registrodevehiculoViewModel {

    private final String VEHICULO_ID = "vehiculoID";
    private final String VEHICULO_EDIT_ROUTE_TEMPLATE = "registro-vehiculo/%s/edit";

    private final Grid<Vehiculo> grid = new Grid<>(Vehiculo.class, false);

    private TextField id_vehiculo;
    private TextField nombre_cliente;
    private TextField marca;
    private TextField modelo;
    private TextField placa;
    private List<Vehiculo> vehiculos;
    
    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    private Vehiculo vehiculo;
    private OrdenVehiculos_Interactor controlador;
    
    public RegistrodeVehículoView() {
   
        addClassNames("registrode-vehículo-view");
        vehiculos = new ArrayList<>();
        this.controlador = new OrdenVehiculos_InteractorImp(this);
        
        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn(Vehiculo::getId_vehiculo).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Vehiculo::getNombre_cliente).setHeader("Cliente").setAutoWidth(true);
        grid.addColumn(Vehiculo::getMarca).setHeader("Marca").setAutoWidth(true);
        grid.addColumn(Vehiculo::getModelo).setHeader("Modelo").setAutoWidth(true);
        grid.addColumn(Vehiculo::getPlaca).setHeader("Placa").setAutoWidth(true);
        /*grid.setItems(query -> vehiculoService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());*/
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(VEHICULO_EDIT_ROUTE_TEMPLATE, event.getValue().getId_vehiculo()));
            } else {
                clearForm();
                UI.getCurrent().navigate(RegistrodeVehículoView.class);
            }
        });

        //Mndo a traer los vhiculos del repositorio
        this.controlador.consultarVehiculo();
        
        // Configure Form
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.vehiculo == null) {
                    this.vehiculo = new Vehiculo();
                }
                //vehiculoService.update(this.vehiculo);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(RegistrodeVehículoView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> vehiculoId = event.getRouteParameters().get(VEHICULO_ID);
        boolean encontrado = false;
        if (vehiculoId.isPresent()) {
        	
        	for(Vehiculo e: this.vehiculos) {
        		if(e.getId_vehiculo().equals(vehiculoId.get())) {
        			populateForm(e);
        			encontrado = true;
        			break;
        		}
        	}
        	
        	if(!encontrado) {
        		Notification.show(String.format("El Vehiculo con ID = %s", vehiculoId.get()+" no fue encontrado"),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(RegistrodeVehículoView.class);
        	}
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id_vehiculo = new TextField("ID");
        nombre_cliente = new TextField("Cliente");
        marca = new TextField("Marca");
        modelo = new TextField("Modelo");
        placa = new TextField("Placa");
        formLayout.add(id_vehiculo, nombre_cliente, marca, modelo, placa);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Vehiculo value) {
    	this.vehiculo = value;
        if(value == null){
        	this.id_vehiculo.setValue("");
            this.nombre_cliente.setValue("");
            this.marca.setValue("");
            this.modelo.setValue("");
            this.placa.setValue("");
        } else {
        	this.id_vehiculo.setValue(value.getId_vehiculo());
            this.nombre_cliente.setValue(value.getNombre_cliente());
            this.marca.setValue(value.getMarca());
            this.modelo.setValue(value.getModelo());
            this.placa.setValue(value.getPlaca());
        }
    }

	@Override
	public void refrescarGridVehiculos(List<Vehiculo> vehiculos) {
		Collection<Vehiculo> items = vehiculos;
		grid.setItems(items);
		this.vehiculos = vehiculos;
		
	}
}
