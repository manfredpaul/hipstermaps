import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SpatialeventComponent } from './spatialevent.component';
import { SpatialeventDetailComponent } from './spatialevent-detail.component';
import { SpatialeventPopupComponent } from './spatialevent-dialog.component';
import { SpatialeventDeletePopupComponent } from './spatialevent-delete-dialog.component';

export const spatialeventRoute: Routes = [
    {
        path: 'spatialevent',
        component: SpatialeventComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hipstermapsApp.spatialevent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'spatialevent/:id',
        component: SpatialeventDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hipstermapsApp.spatialevent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const spatialeventPopupRoute: Routes = [
    {
        path: 'spatialevent-new',
        component: SpatialeventPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hipstermapsApp.spatialevent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'spatialevent/:id/edit',
        component: SpatialeventPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hipstermapsApp.spatialevent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'spatialevent/:id/delete',
        component: SpatialeventDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hipstermapsApp.spatialevent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
