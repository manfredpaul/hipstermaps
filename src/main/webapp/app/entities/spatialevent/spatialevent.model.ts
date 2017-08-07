import { BaseEntity } from './../../shared';

export class Spatialevent implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public date?: any,
        public locationContentType?: string,
        public location?: any,
    ) {
    }
}
