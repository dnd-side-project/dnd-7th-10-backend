package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
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
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.FOLDER_NOT_FOUND)));
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

    public FolderResponse edit(UUID uuid, FolderRequest folderRequest) {
        Folder folder = folderRepository.findById(uuid)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.POSTS_NOT_FOUND));

        folder.changeFolderTitle(folderRequest.getFolderTitle());
        return FolderMapper.INSTANCE.toResponseDto(folderRepository.save(folder));
    }
}
