package com.tallervehiculos.uth.views.detallesdeorden;

import com.tallervehiculos.uth.data.entity.Detalles;
import com.tallervehiculos.uth.views.MainLayout;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
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
import com.vaadin.flow.component.treegrid.TreeGrid;

@PageTitle("Detalles de Orden")
@Route(value = "detalles-orden", layout = MainLayout.class)
@Uses(Icon.class)
public class DetallesdeOrdenView extends Div {

    private Grid<Detalles> grid;

    //private final SamplePersonService samplePersonService;

    public DetallesdeOrdenView() {
        setSizeFull();
        addClassNames("detallesde-orden-view");

        VerticalLayout layout = new VerticalLayout( createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private Component createGrid() {
        grid = new Grid<>(Detalles.class, false);
        grid.addColumn(Detalles::getCliente).setHeader("Clientes");
        grid.addColumn(Detalles::getPlaca).setHeader("Placa");
        grid.addColumn(Detalles::getServicios).setHeader("Servicios");
        grid.addColumn(Detalles::getRepuestos).setHeader("Repuestos");
        grid.addColumn(Detalles::getTotal).setHeader("Total");
        /*grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());*/
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }
 
    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

}
