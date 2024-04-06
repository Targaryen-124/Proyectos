package hn.lacolonia.views.proveedores;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;

import hn.lacolonia.controller.InteractorImplProveedores;
import hn.lacolonia.controller.InteractorProveedores;
import hn.lacolonia.data.Proveedor;
import hn.lacolonia.views.MainLayout;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@PageTitle("Proveedores")
@Route(value = "proveedores", layout = MainLayout.class)
@Uses(Icon.class)

public class ProveedoresView extends Div implements ViewModelProveedores {

    private Grid<Proveedor> grid;
    private InteractorProveedores controlador;
    private Filters filters;
    private List<Proveedor> elementos;
    public ProveedoresView() {

        setSizeFull();
        addClassNames("proveedores-View");

        filters = new Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
        elementos = new ArrayList<>();
        controlador = new InteractorImplProveedores(this);
        controlador.consultarProveedores();
    }

    
    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div implements Specification<Proveedor> {

    	private final TextField idproveedor = new TextField("ID");
        private final TextField nombre = new TextField("Nombre");
        private final TextField direccion = new TextField("Direccion");
        private final TextField telefono = new TextField("Telefono");
        
        public Filters(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            
            // Action buttons
            Button cancelBtn = new Button("Cancelar", new Icon(VaadinIcon.CLOSE_CIRCLE));
            cancelBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
            cancelBtn.setId("btn_cancelar");
            cancelBtn.addClickListener(e -> {
            	idproveedor.clear();
                nombre.clear();
                direccion.clear();
                telefono.clear();
                onSearch.run();
            });
            
            Button searchBtn = new Button("Buscar", new Icon(VaadinIcon.SEARCH));
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());
            
           /* Button deleteBtn = new Button("Eliminar", new Icon(VaadinIcon.TRASH));
            deleteBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            deleteBtn.setId("btn_eliminar");
            deleteBtn.addClickListener(e -> {
            	Notification n = Notification.show("Boton Eliminar Seleccionado, Aun no hay Nada que Eliminar");
            	n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_WARNING);
            });*/
            
            Div actions = new Div(searchBtn, cancelBtn); //deleteBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            idproveedor.setId("txt_idproveedor");
            idproveedor.setPrefixComponent(VaadinIcon.ELLIPSIS_DOTS_H.create());
            
            nombre.setId("txt_nombre");
            nombre.setPrefixComponent(VaadinIcon.CLIPBOARD_USER.create());
            
            direccion.setId("txt_direccion");
            direccion.setPrefixComponent(VaadinIcon.MAP_MARKER.create());
            
            telefono.setId("txt_telefono");
            telefono.setPrefixComponent(VaadinIcon.PHONE.create());
            
            add(idproveedor, nombre, direccion, telefono, actions);
        }

        @Override
        public Predicate toPredicate(Root<Proveedor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!idproveedor.isEmpty()) {
            	String lowerCaseFilter = idproveedor.getValue().toLowerCase();
                Predicate idMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("idproveedor")),
                        lowerCaseFilter + "%");
                predicates.add(idMatch);
            }
            
            if (!nombre.isEmpty()) {
                String lowerCaseFilter = nombre.getValue().toLowerCase();
                Predicate nameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        lowerCaseFilter + "%");
                predicates.add(nameMatch);
            }
            
            if (!direccion.isEmpty()) {
                String lowerCaseFilter = direccion.getValue().toLowerCase();
                Predicate direccionMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        lowerCaseFilter + "%");
                predicates.add(direccionMatch);
            }
            
            if (!telefono.isEmpty()) {
                String databaseColumn = "telefono";
                String ignore = "- ()";

                String lowerCaseFilter = ignoreCharacters(ignore, telefono.getValue().toLowerCase());
                Predicate telefonoMatch = criteriaBuilder.like(
                        ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                        "%" + lowerCaseFilter + "%");
                predicates.add(telefonoMatch);
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }

        private String ignoreCharacters(String characters, String in) {
            String result = in;
            for (int i = 0; i < characters.length(); i++) {
                result = result.replace("" + characters.charAt(i), "");
            }
            return result;
        }

        private Expression<String> ignoreCharacters(String characters, CriteriaBuilder criteriaBuilder,
                Expression<String> inExpression) {
            Expression<String> expression = inExpression;
            for (int i = 0; i < characters.length(); i++) {
                expression = criteriaBuilder.function("replace", String.class, expression,
                        criteriaBuilder.literal(characters.charAt(i)), criteriaBuilder.literal(""));
            }
            return expression;
        }

    }

    private Component createGrid() {
        grid = new Grid<>(Proveedor.class, false);
        grid.addColumn("idproveedor").setAutoWidth(true);
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("direccion").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }
   
    
    @Override
	public void mostrarProveedoresEnGrid(List<Proveedor> items) {
		Collection<Proveedor> itemsCollection = items;
		grid.setItems(itemsCollection);
		this.elementos = items;
	}
    
    @Override
	public void mostrarMensajeError(String mensaje) {
		Notification.show(mensaje);
	}

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

}
