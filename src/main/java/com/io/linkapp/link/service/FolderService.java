package com.io.linkapp.link.service;

import com.io.linkapp.exception.FolderNotFoundException;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.mapper.FolderMapper;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.user.domain.User;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;


    public FolderResponse get(UUID uuid) {
        return FolderMapper.INSTANCE.toResponseDto(folderRepository.findById(uuid)
            .orElseThrow(FolderNotFoundException::new));
    }

    public FolderResponse add(FolderRequest folderRequest, User user) {
        folderRequest.setUser(user);
        Folder folder = FolderMapper.INSTANCE.toEntity(folderRequest);
        return FolderMapper.INSTANCE.toResponseDto(folderRepository.save(folder));
    }

    public List<FolderResponse> getFoldersByUser(User user) {
        return folderRepository.findByUser(user)
            .stream().map(FolderMapper.INSTANCE::toResponseDto)
            .collect(Collectors.toList());
    }

    public void remove(UUID uuid){
        folderRepository.deleteById(uuid);
    }
}
