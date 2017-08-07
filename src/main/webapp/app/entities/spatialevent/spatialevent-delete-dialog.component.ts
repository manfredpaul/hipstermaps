import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Spatialevent } from './spatialevent.model';
import { SpatialeventPopupService } from './spatialevent-popup.service';
import { SpatialeventService } from './spatialevent.service';

@Component({
    selector: 'jhi-spatialevent-delete-dialog',
    templateUrl: './spatialevent-delete-dialog.component.html'
})
export class SpatialeventDeleteDialogComponent {

    spatialevent: Spatialevent;

    constructor(
        private spatialeventService: SpatialeventService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.spatialeventService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'spatialeventListModification',
                content: 'Deleted an spatialevent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-spatialevent-delete-popup',
    template: ''
})
export class SpatialeventDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spatialeventPopupService: SpatialeventPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.spatialeventPopupService
                .open(SpatialeventDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
