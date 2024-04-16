package com.tallervehiculos.uth.views.ordendereparación;

import com.tallervehiculos.uth.data.controller.OrdenReparacion_Interactor;
import com.tallervehiculos.uth.data.controller.OrdenReparacion_InteractorImp;
import com.tallervehiculos.uth.data.controller.OrdenVehiculos_Interactor;
import com.tallervehiculos.uth.data.controller.OrdenVehiculos_InteractorImp;
import com.tallervehiculos.uth.data.entity.Orden_reparacion;
import com.tallervehiculos.uth.data.entity.Vehiculo;
import com.tallervehiculos.uth.views.MainLayout;
import com.tallervehiculos.uth.views.registrodevehículo.RegistrodeVehículoView;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Orden de Reparación")
@Route(value = "orden-reparacion/:orden_reparacionID?/:action?(edit)", layout = MainLayout.class)
public class OrdendeReparaciónView extends Div implements BeforeEnterObserver, OrdendeReparacionViewModel {

    private final String ORDEN_REPARACION_ID = "orden_reparacionID";
    private final String ORDEN_REPARACION_EDIT_ROUTE_TEMPLATE = "orden-reparacion/%s/edit";

    private final Grid<Orden_reparacion> grid = new Grid<>(Orden_reparacion.class, false);

    private TextField id_orden;
    private TextField vehiculo_id;
    private TextField descripcion_problema;
    private TextField estado_reparacion;
    private List<Orden_reparacion> orden;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    
    private Orden_reparacion ordenes;
    private OrdenReparacion_Interactor controlador;
  
    public OrdendeReparaciónView() {

        addClassNames("ordende-reparación-view");
        orden = new ArrayList<>();
        this.controlador = new OrdenReparacion_InteractorImp(this);

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn(Orden_reparacion::getId_orden).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Orden_reparacion::getVehiculo_id).setHeader("Vehiculo ID").setAutoWidth(true);
        //grid.addColumn(Orden_reparacion::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Orden_reparacion::getDescripcion_problema).setHeader("Problema").setAutoWidth(true);
        grid.addColumn(Orden_reparacion::getEstado_reparacion).setHeader("Estado").setAutoWidth(true);
        /*grid.setItems(query -> orden_reparacionService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());*/
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ORDEN_REPARACION_EDIT_ROUTE_TEMPLATE, event.getValue().getId_orden()));
            } else {
                clearForm();
                UI.getCurrent().navigate(OrdendeReparaciónView.class);
            }
        });

      //Mndo a traer las ordenes del repositorio
        this.controlador.consultarOrden();
        
        // Configure Form
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.ordenes == null) {
                    this.ordenes = new Orden_reparacion();
                }
                //.update(this.orden_reparacion);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(OrdendeReparaciónView.class);
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
        Optional<String> orden_reparacionId = event.getRouteParameters().get(ORDEN_REPARACION_ID);
        boolean encontrado = false;
        if (orden_reparacionId.isPresent()) {
        	for(Orden_reparacion e: this.orden) {
        		if(e.getId_orden().equals(orden_reparacionId.get())) {
        			populateForm(e);
        			encontrado = true;
        			break;
        		}
        	}
        	if(!encontrado) {
        		Notification.show(String.format("La orden de reparacion con ID = %s", orden_reparacionId.get()+" no fue encontrada"),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(OrdendeReparaciónView.class);
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
        id_orden = new TextField("ID");
        vehiculo_id = new TextField("Vehiculo ID");
        descripcion_problema = new TextField("Problema");
        estado_reparacion = new TextField("Estado");
        formLayout.add(id_orden, vehiculo_id, descripcion_problema, estado_reparacion);

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

    private void populateForm(Orden_reparacion value) {
    	this.ordenes = value;
    	
        if(value == null){
        	this.id_orden.setValue("");
            this.vehiculo_id.setValue("");
            this.descripcion_problema.setValue("");
            this.estado_reparacion.setValue("");
        } else {
        	this.id_orden.setValue(value.getId_orden());
            this.vehiculo_id.setValue(value.getVehiculo_id());
            this.descripcion_problema.setValue(value.getDescripcion_problema());
            this.estado_reparacion.setValue(value.getEstado_reparacion());

        }
    }
    
    @Override
	public void refrescarGridOrden(List<Orden_reparacion> items_orden) {
		Collection<Orden_reparacion> items = items_orden;
		grid.setItems(items);
		this.orden = items_orden;
		
	}
}
