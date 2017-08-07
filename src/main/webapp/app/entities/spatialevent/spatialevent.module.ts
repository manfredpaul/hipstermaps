import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HipstermapsSharedModule } from '../../shared';
import {
    SpatialeventService,
    SpatialeventPopupService,
    SpatialeventComponent,
    SpatialeventDetailComponent,
    SpatialeventDialogComponent,
    SpatialeventPopupComponent,
    SpatialeventDeletePopupComponent,
    SpatialeventDeleteDialogComponent,
    spatialeventRoute,
    spatialeventPopupRoute,
} from './';

const ENTITY_STATES = [
    ...spatialeventRoute,
    ...spatialeventPopupRoute,
];

@NgModule({
    imports: [
        HipstermapsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SpatialeventComponent,
        SpatialeventDetailComponent,
        SpatialeventDialogComponent,
        SpatialeventDeleteDialogComponent,
        SpatialeventPopupComponent,
        SpatialeventDeletePopupComponent,
    ],
    entryComponents: [
        SpatialeventComponent,
        SpatialeventDialogComponent,
        SpatialeventPopupComponent,
        SpatialeventDeleteDialogComponent,
        SpatialeventDeletePopupComponent,
    ],
    providers: [
        SpatialeventService,
        SpatialeventPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipstermapsSpatialeventModule {}
