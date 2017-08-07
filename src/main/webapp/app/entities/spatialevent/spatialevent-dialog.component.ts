import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Spatialevent } from './spatialevent.model';
import { SpatialeventPopupService } from './spatialevent-popup.service';
import { SpatialeventService } from './spatialevent.service';

@Component({
    selector: 'jhi-spatialevent-dialog',
    templateUrl: './spatialevent-dialog.component.html'
})
export class SpatialeventDialogComponent implements OnInit {

    spatialevent: Spatialevent;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private spatialeventService: SpatialeventService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, spatialevent, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                spatialevent[field] = base64Data;
                spatialevent[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.spatialevent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.spatialeventService.update(this.spatialevent));
        } else {
            this.subscribeToSaveResponse(
                this.spatialeventService.create(this.spatialevent));
        }
    }

    private subscribeToSaveResponse(result: Observable<Spatialevent>) {
        result.subscribe((res: Spatialevent) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Spatialevent) {
        this.eventManager.broadcast({ name: 'spatialeventListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-spatialevent-popup',
    template: ''
})
export class SpatialeventPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spatialeventPopupService: SpatialeventPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.spatialeventPopupService
                    .open(SpatialeventDialogComponent as Component, params['id']);
            } else {
                this.spatialeventPopupService
                    .open(SpatialeventDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
