package hn.lacolonia.views.productos;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import hn.lacolonia.controller.InteractorImplProductos;
import hn.lacolonia.controller.InteractorProductos;
import hn.lacolonia.data.Categoria;
import hn.lacolonia.data.Producto;
import hn.lacolonia.data.SamplePerson;
import hn.lacolonia.views.MainLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Productos")
@Route(value = "productos/:codigo?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class ProductosView extends Div implements BeforeEnterObserver, ViewModelProductos {

	private final String PRODUCT_CODIGO = "codigo";
    private final String PRODUCT_EDIT_ROUTE_TEMPLATE = "productos/%s/edit";

    private final Grid<Producto> grid = new Grid<>(Producto.class, false);

    private TextField codigo;
    private TextField nombre;
    private NumberField precio;
    private ComboBox<Categoria> categoria;
    private DatePicker fechaVencimiento;

    private final Button cancel = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE));
    private final Button save = new Button("Guardar", new Icon(VaadinIcon.CHECK_CIRCLE));
    private final Button eliminar = new Button("Eliminar", new Icon(VaadinIcon.TRASH));

    private Producto productoSeleccionado;
    private List<Producto> elementos;
    private List<Categoria> categorias;
    private InteractorProductos controlador;
    private Categoria categoriaSeleccionada;
    
    public ProductosView() {
        addClassNames("productos-view");
        
        controlador = new InteractorImplProductos(this);
        elementos = new ArrayList<>();
        categorias = new ArrayList<>();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("codigo").setAutoWidth(true);
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true);
        grid.addColumn("nombre_categoria").setAutoWidth(true).setHeader("Categoria");
        grid.addColumn("fecha_vencimiento").setAutoWidth(true).setHeader("Fecha de Vencimiento");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PRODUCT_EDIT_ROUTE_TEMPLATE, event.getValue().getCodigo()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ProductosView.class);
            }
        });
        
        controlador.consultarProductos();
        controlador.consultarCategorias();

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.productoSeleccionado == null) {
                    this.productoSeleccionado = new Producto();
                    
                    this.productoSeleccionado.setCodigo(codigo.getValue());
                    this.productoSeleccionado.setNombre(nombre.getValue());
                    this.productoSeleccionado.setPrecio(precio.getValue());
                    this.productoSeleccionado.setCategoria(categoria.getValue().getIdcategoria());
                    //this.productoSeleccionado.setFecha_vencimiento(convertDateToLocal(fechaVencimiento.getValue()));
                    
                    this.controlador.crearProductos(productoSeleccionado);
                    
                }else {
                	
                }
                
                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(ProductosView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        
        eliminar.addClickListener(e -> {
        	Notification n = Notification.show("Boton Eliminar Seleccionado, Aun no hay Nada que Eliminar");
        	n.setPosition(Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_WARNING);
        });
    }

    /*private Date convertDateToLocal(LocalDate fecha) {
    	return java.sql.Date.valueOf(fecha);
	}*/

	@Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> codigoProducto = event.getRouteParameters().get(PRODUCT_CODIGO);
        if (codigoProducto.isPresent()) {
        	Producto productoObtenido = obtenerProducto(codigoProducto.get());
            if (productoObtenido != null) {
                populateForm(productoObtenido);
            } else {
                Notification.show(
                        String.format("El producto con codigo = %s no existe", codigoProducto.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ProductosView.class);
            }
        }
    }
    
    private Producto obtenerProducto(String codigo) {
    	Producto encontrado = null;
		for(Producto pro: elementos) {
			if(pro.getCodigo().equals(codigo)) {
				encontrado = pro;
				break;
			}
			
		}
		return encontrado;
	}

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        codigo = new TextField("Codigo");
        codigo.setId("txt_codigo");
        codigo.setPrefixComponent(VaadinIcon.BARCODE.create());
        
        nombre = new TextField("Nombre");
        nombre.setId("txt_nombre");
        nombre.setPrefixComponent(VaadinIcon.CART.create());
        
        precio = new NumberField("Precio");
        precio.setId("txt_precio");
        precio.setLabel("Precio");
        precio.setValue(0.0);
        Div lempiraPrefix = new Div();
        lempiraPrefix.setText("L.");
        precio.setPrefixComponent(lempiraPrefix);
        
        categoria = new ComboBox<>("Categoria");
        categoria.setId("txt_categoria");
        categoria.setPrefixComponent(VaadinIcon.LIST_UL.create());
        categoria.setItemLabelGenerator(Categoria::getNombre);
        
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        fechaVencimiento = new DatePicker("Fecha de Vencimiento");
        fechaVencimiento.setMin(now.plusYears(-2));
        
        formLayout.add(codigo, nombre, precio, categoria, fechaVencimiento);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        cancel.setId("btn_cancelar");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setId("btn_guardar");
        eliminar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        eliminar.setId("btn_eliminar");
        
        buttonLayout.add(save, cancel, eliminar);
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
        this.controlador.consultarProductos();
        this.controlador.consultarCategorias();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Producto value) {
    	 this.productoSeleccionado = value;
         if(value != null) {
         	 codigo.setValue(value.getCodigo());
             nombre.setValue(value.getNombre());
             precio.setValue(value.getPrecio());
             
             categoriaSeleccionada = buscarCategoria(value.getCategoria());
             categoria.setValue(categoriaSeleccionada);
         }else {
         	 codigo.setValue("");
             nombre.setValue("");
             precio.setValue(0.0);
             categoria.clear();
         }
    }
    
    private Categoria buscarCategoria(int idCategoria) {
    	Categoria encontrada = null;
		for(Categoria cat: categorias) {
			if(cat.getIdcategoria() == idCategoria) {
				encontrada = cat;
				break;
			}
			
		}
		return encontrada;
	}

	@Override
	public void mostrarProductosEnGrid(List<Producto> items) {
		Collection<Producto> itemsCollection = items;
		grid.setItems(itemsCollection);
		this.elementos = items;
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		Notification.show(mensaje);
	}

	@Override
	public void mostrarCategoriasEnCombobox(List<Categoria> items) {
		Collection<Categoria> itemsCollection = items;
		categorias = items;
		categoria.setItems(items);	
	}

	@Override
	public void mostrarMensajeExito(String mensaje) {
		Notification.show(mensaje);
	}
}
