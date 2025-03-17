package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.SupplierDTO;
import org.example.warehouse_managment.exceptions.SupplierNotFoundException;
import org.example.warehouse_managment.mappers.SupplierMapper;
import org.example.warehouse_managment.model.Supplier;
import org.example.warehouse_managment.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(int id) throws SupplierNotFoundException {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isEmpty()) {
            throw new SupplierNotFoundException("Supplier with ID " + id + " not found.");
        }
        return supplier.get();
    }

    public Supplier saveSupplier(SupplierDTO supplierDTO) throws SupplierNotFoundException {
        Supplier supplier = SupplierMapper.INSTANCE.toSupplier(supplierDTO);

        return supplierRepository.save(supplier);
    }


    public Supplier updateSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(int id) {
        supplierRepository.deleteById(id);
    }
}
