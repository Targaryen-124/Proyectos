package com.tallervehiculos.uth.views;

import com.tallervehiculos.uth.views.detallesdeorden.DetallesdeOrdenView;
import com.tallervehiculos.uth.views.ordendereparación.OrdendeReparaciónView;
import com.tallervehiculos.uth.views.ordensr.OrdenSRView;
import com.tallervehiculos.uth.views.registrodevehículo.RegistrodeVehículoView;
import com.tallervehiculos.uth.views.repuestos.RepuestosView;
import com.tallervehiculos.uth.views.servicios.ServiciosView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Taller_Vehiculos");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Registro de Vehículo", RegistrodeVehículoView.class,
                LineAwesomeIcon.CAR_SOLID.create()));
        nav.addItem(new SideNavItem("Orden de Reparación", OrdendeReparaciónView.class,
                LineAwesomeIcon.FILE_INVOICE_SOLID.create()));
        nav.addItem(new SideNavItem("Orden S/R", OrdenSRView.class, LineAwesomeIcon.FILE_INVOICE_SOLID.create()));
        nav.addItem(new SideNavItem("Servicios", ServiciosView.class, LineAwesomeIcon.LIST_OL_SOLID.create()));
        nav.addItem(new SideNavItem("Repuestos", RepuestosView.class, LineAwesomeIcon.LIST_OL_SOLID.create()));
        nav.addItem(new SideNavItem("Detalles de Orden", DetallesdeOrdenView.class, LineAwesomeIcon.LIST_ALT.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
